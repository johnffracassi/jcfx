package com.jeff.fx.backtest.strategy;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.OrderBookReport;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserExecutor;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserParameter;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserReportRow;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserView;
import com.jeff.fx.backtest.strategy.optimiser.Permutator;
import com.jeff.fx.backtest.strategy.time.IndicatorCache;
import com.jeff.fx.backtest.strategy.time.TimeStrategy;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.TimeOfWeek;

public class MultiThreadedExecutor implements OptimiserExecutor {

	private static Logger log = Logger.getLogger(MultiThreadedExecutor.class);
	
	// shared user feedback
	private OptimiserView view;

	// shared, accessed by multiple threads
	private volatile boolean running = false;

	// shared
	private List<OptimiserParameter<?,?>> params;
	private Permutator permutator;
	
	// used by manager
	private int jobSize = 50;
	private int threadCount = 1;
	
	/**
	 * Create and start the executor threads
	 */
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

		// get the parameters for the executor
		threadCount = AppCtx.getPersistentInt("multiThreadExecutor.threads");
		jobSize = AppCtx.getPersistentInt("multiThreadExecutor.blockSize");

		// execute the tests asynchronously
		Manager manager = new Manager(candles, permutator.getPermutationCount());
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
	
	class Manager implements Runnable {
		
		// pointer to the start of the next job
		private volatile int blockPointer = 0;
		
		// total number of jobs completed 
		private volatile int completedCount = 0;
		private volatile boolean firstEntry = true;
		
		// start time of the batch (in nano seconds)
		private volatile long start = 0;
		
		// total number of permutations, including discarded & invalid permutations
		private final int permutations;
		
		// all candles required for the batch
		private volatile CandleCollection candles;
		
		// all indicators required for the batch
		private volatile IndicatorCache indicators = new IndicatorCache();
		
		// worker threads. probably should be pooled?
		private volatile ThreadGroup workerGroup;
		private volatile List<WorkerThread> workerList = new ArrayList<MultiThreadedExecutor.Manager.WorkerThread>();

		// semaphores
		private final Object jobAllocationSemaphore = new Object();
		private final Object counterSemaphore = new Object();
		
		public Manager(CandleCollection candles, int permutations) {
			
			log.debug("creating multi-threaded executor manager");
			
			this.candles = candles;
			this.permutations = permutations;

			// create and execute workers
			workerGroup = new ThreadGroup("executor");
			for(int w=0; w<threadCount; w++) {
				workerList.add(new WorkerThread(workerGroup, workerGroup.getName() + "-" + (w+1)));
			}
		}
		
		public void run() {
			
			log.debug("starting multi-threaded executor manager");

			running = true;
			start = System.nanoTime();

			// start the thread group
			for(Thread thread : workerList) {
				thread.start();
			} 
		}
		
		/**
		 * are there more jobs to be executed?
		 * @return
		 */
		public boolean hasNextJob() {
			
			synchronized(jobAllocationSemaphore) {
				return (blockPointer < permutations) && running;
			}
		}
		
		/**
		 * 
		 * @return
		 */
		public ExecutorJob getNextJob() {
			
			// update display
			updateStatus();

			// increment job start index and allocate a new job block
			synchronized(jobAllocationSemaphore) {
				
				blockPointer += jobSize;
				
				int startIdx = blockPointer;
				int endIdx = min(blockPointer + jobSize, permutations);
				
				return new ExecutorJob(startIdx / jobSize, startIdx, endIdx);
			}
		}
		
		private void updateStatus() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					// display the results
					view.getLblCompleted().setText(String.format("Completed: %d (%.1f%%)", completedCount, ((double)completedCount / permutator.getPermutationCount()) * 100.0));
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
		
		public void jobCompleted(ExecutorJob job) {
			synchronized(counterSemaphore) {
				completedCount += (job.endIdx - job.startIdx);
			}
		}
		
		/**
		 * 
		 */
		class WorkerThread extends Thread {
			
			public WorkerThread(ThreadGroup group, String name) {
				super(group, name);
				log.debug("created new worker: " + this.getName());
			}
			
			public void run() {
				
				log.debug("starting multi-threaded worker (" + getName() + ")");

				while(hasNextJob()) {
					
					ExecutorJob job = getNextJob();
					
					job.setStartTime(System.nanoTime());
					for(int i=job.startIdx; i<job.endIdx; i++) {
						executeSingleTest(i);
					}
					job.setEndTime(System.nanoTime());

					jobCompleted(job);					
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
				if(test.isTestValid()) {
					test.execute(candles, indicators);
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

