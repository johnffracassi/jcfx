package com.jeff.fx.backtest.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.OrderBookReport;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserExecutor;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserParameter;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserReportRow;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserView;
import com.jeff.fx.backtest.strategy.optimiser.Permutator;
import com.jeff.fx.backtest.strategy.time.TimeStrategy;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.TimeOfWeek;

public class MultiThreadedExecutor implements OptimiserExecutor {
	
	private Permutator permutator;
	private List<OptimiserParameter> params;
	private OptimiserView view;
	
	private boolean running = false;
	private int blockSize = 50;
	private int threadCount = 1;
	
	public void run(CandleCollection candles, OptimiserView view) {
		
		this.view = view;
		
		// find the number of steps for each param
		params = view.getParameters();
		int[] perms = new int[params.size()];
		for(int i=0; i<params.size(); i++) {
			perms[i] = params.get(i).getStepCount();
		}
		
		// build the permutator
		permutator = new Permutator(perms);
		view.getLblPermutations().setText("Permutations: " + permutator.getPermutationCount());
		view.getLblCompleted().setText("Completed: 0");
		
		// execute the tests asynchronously
		threadCount = AppCtx.getPersistentInt("multiThreadExecutor.threads");
		blockSize = AppCtx.getPersistentInt("multiThreadExecutor.blockSize");
		Manager manager = new Manager(threadCount, candles, permutator.getPermutationCount());
		manager.run();
	}

	public void stop() {
		pause();
	}

	public void pause() {
		running = false;
	}

	public void resume() {
		running = true;
	}

	public void reset() {
		stop();
	}
	
	class Manager {
		
		private Worker[] workers;
		private int blockPointer = 0;
		private int permutations = 0;
		private int completedCount = 0;
		private long start = 0;
		
		private Object jobAllocationSemaphore = new Object();
		private Object counterSemaphore = new Object();
		private List<Job> jobs;

		private CandleCollection candles;

		public Manager(int threads, CandleCollection candles, int permutations) {
			
			this.candles = candles;
			this.permutations = permutations;
			jobs = new ArrayList<Job>(permutations);
			
			this.workers = new Worker[threads];
			for(int w=0; w<workers.length; w++) {
				workers[w] = new Worker();
			}
		}
		
		public void run() {
			running = true;
			start = System.nanoTime();
			for(int w=0; w<workers.length; w++) {
				workers[w].start();
			}
		}
		
		public boolean hasNextBlock() {
			return (blockPointer < permutations) && running;
		}
		
		public Job getNextJob() {
			synchronized(jobAllocationSemaphore) {
				updateStatus();
				blockPointer += blockSize;
				Job job = new Job(1, blockPointer, blockPointer + blockSize);
				return job;
			}
		}
		
		private void updateStatus() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// display the results
					view.getLblCompleted().setText("Completed: " + completedCount);
					view.getProgressBar().setValue(completedCount);
					view.getProgressBar().setMaximum(permutations);
					
					double elapsed = (System.nanoTime() - start) / 1000000000.0;
					double remaining = (elapsed / completedCount) * (permutator.getPermutationCount() - completedCount);
					view.getLblElapsedTime().setText(String.format("Elapsed Time: %.2fs", elapsed));
					view.getLblRemainingTime().setText(String.format("Remaining Time: %.2fs", remaining));
					view.getLblSpeed().setText(String.format("Tests/min: %.0f", completedCount / (elapsed / 60.0)));
				}
			});
		}
		
		public void blockCompleted(Job job) {
			synchronized(counterSemaphore) {
				completedCount += (job.endIdx - job.startIdx);
				jobs.add(job);
			}
		}

		class Job {
			
			public int id;
			public int startIdx;
			public int endIdx;
			public long start;
			public long end;
			
			public Job(int id, int startIdx, int endIdx) {
				super();
				this.id = id;
				this.startIdx = startIdx;
				this.endIdx = endIdx;
			}
		}
		
		class Worker extends Thread {
			
			public void run() {
				
				while(hasNextBlock()) {
					
					Job job = getNextJob();
					
					job.start = System.nanoTime();
					for(int i=job.startIdx; i<job.endIdx; i++) {
						executeSingleTest(i);
					}
					job.end = System.nanoTime();

					blockCompleted(job);					
				}
			}
	
			private void executeSingleTest(int idx) {
				
				// get the parameter values for this single test
				int[] valueIndexes = permutator.getPermutation(idx);
				Object[] values = new Object[valueIndexes.length];
				for(int v=0; v<values.length; v++) {
					values[v] = params.get(v).getValue(valueIndexes[v]);
				}
		
				// create a map of the parameters for this run
				final Map<String,Object> map = new HashMap<String,Object>();
				map.put("stopLoss", values[0]);
				map.put("takeProfit", values[1]);
				map.put("open", new TimeOfWeek((Integer)values[2]));
				map.put("close", new TimeOfWeek((Integer)values[3]));
				map.put("offerSide", values[4]);
				
				// build the test
				final TimeStrategy test = new TimeStrategy(idx, map);
				if(test.runTest()) {
					test.execute(candles);
				}
				
				// check the report and add if interesting
				final OrderBookReport report = new OrderBookReport(test.getOrderBook());
				if(view.getReportModel().accepts(report)) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							view.getReportModel().addRow(new OptimiserReportRow(test.getId(), report, map));
						}
					});
				}
			}
		}
	}
}
