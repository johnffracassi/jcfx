package com.jeff.fx.backtest.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.jeff.fx.backtest.engine.OrderBookReport;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserExecutor;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserParameter;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserReportRow;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserView;
import com.jeff.fx.backtest.strategy.optimiser.Permutator;
import com.jeff.fx.backtest.strategy.time.TimeStrategy;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.TimeOfWeek;


public class SingleThreadedExecutor implements OptimiserExecutor {
	
	public void run(final CandleCollection candles, final OptimiserView view) {
		
		@SuppressWarnings("rawtypes")
		List<OptimiserParameter> params = view.getParameters();
		
		// find the number of steps for each param
		int[] perms = new int[params.size()];
		for(int i=0; i<params.size(); i++) {
			perms[i] = params.get(i).getStepCount();
		}
		
		// build the permutator
		final Permutator permutator = new Permutator(perms);
		view.getLblPermutations().setText("Permutations: " + permutator.getPermutationCount());
		view.getLblCompleted().setText("Completed: 0");
		
		// execute the tests sequentially
		final long start = System.nanoTime();
		int totalPerms = permutator.getPermutationCount();
		for(int i=0; i<totalPerms; i++) {
			
			// get the parameter values for this single test
			int[] valueIndexes = permutator.getPermutation(i);
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
			
			// build the test
			final TimeStrategy test = new TimeStrategy(i, map);
//			test.execute(candles);
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// check the report and add if interesting
					OrderBookReport report = new OrderBookReport(test.getOrderBook());
					if(report.getWinPercentage() > 65) {
						view.getReportModel().addRow(new OptimiserReportRow(test.getId(), report, map));
					}

					// display the results
					int completed = (test.getId() + 1);
					view.getLblCompleted().setText("Completed: " + completed);
					view.getProgressBar().setValue(completed);
					view.getProgressBar().setMaximum(permutator.getPermutationCount());
					
					double elapsed = (System.nanoTime() - start) / 1000000000.0;
					double remaining = (elapsed / completed) * (permutator.getPermutationCount() - completed);
					view.getLblElapsedTime().setText(String.format("Elapsed Time: %.2fs", elapsed));
					view.getLblRemainingTime().setText(String.format("Remaining Time: %.2fs", remaining));
				}
			});
		}
	}

	public void pause() {
	}

	public void resume() {
	}

	public void stop() {
	}

	public void reset() {
	}
}