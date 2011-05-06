package com.jeff.fx.backtest.strategy.optimiser;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.strategy.*;
import com.jeff.fx.backtest.strategy.time.StrategyView;
import com.jeff.fx.common.CandleCollection;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OptimiserController implements ExecutorJobListener, ExecutorStatusListener {

	private ExecutorParametersController executorParametersController = new ExecutorParametersController();
	private CandleCollection candles;
	private OptimiserView view;
	private OptimiserExecutor executor = new MultiThreadedExecutor();
	
	public OptimiserController(final StrategyView parent) {
		
		view = new OptimiserView();
		executorParametersController.setView(view.getPnlExecutorParameters());
		
		view.getRunButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runOptimiser();
			}
		});
		
		view.getBtnPause().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseOptimiser();
			}
		});
		
		view.getReportTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if(ev.getClickCount() == 2 && ev.getButton() == MouseEvent.BUTTON1) {
					int testIdx = view.getReportTable().convertRowIndexToModel(view.getReportTable().getSelectedRow());
					parent.setParams(view.getReportModel().getRow(testIdx).getParams());
				}
			}
		});
		
		// pack the columns when there is data in it
		view.getReportModel().addTableModelListener(new TableModelListener() {
			private boolean packed = false;
			public void tableChanged(TableModelEvent ev) {
				if(!packed && view.getReportModel().getRowCount() > 0) {
					view.getReportTable().packAll();
					System.out.println("packing table...");
					packed = true;
				} else if(view.getReportModel().getRowCount() == 0){ 
					packed = false;
				}					
			}
		});
	}
	
	public OptimiserView getView() {
		return view;
	}
	
	public void setCandles(CandleCollection candles) {
		this.candles = candles;
	}
	
	public void pauseOptimiser() {
		executor.pause();
	}
	
	public void jobExecuted(ExecutorJobResult results) {
		for(ExecutorTaskResult etr : results.getTaskResults()) {
			view.getReportModel().addRow(new OptimiserReportRow(etr.getId(), etr.getBookReport(), etr.getParams()));
		}
	}
	
	public void executorStatusUpdate(final ExecutorStatus status) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				// display the results
				view.getLblCompleted().setText(String.format("Completed: %d (%.1f%%)", status.getTasksExecuted(), ((double)status.getTasksExecuted() / status.getTotalTaskCount()) * 100.0));
				view.getProgressBar().setValue(status.getTasksExecuted());
				view.getProgressBar().setMaximum(status.getTotalTaskCount());
				
				double elapsed = status.getElapsedTime() / 1000000000.0;
				double remaining = (elapsed / status.getTasksExecuted()) * (status.getTotalTaskCount() - status.getTasksExecuted());
				view.getLblElapsedTime().setText(String.format("Elapsed Time: %.2fs", elapsed));
				view.getLblRemainingTime().setText(String.format("Remaining Time: %.2fs", remaining));
				view.getLblSpeed().setText(String.format("Tests/min: %.0f", status.getTasksExecuted() / (elapsed / 60.0)));
			}
		});
	}

	public void runOptimiser() {
		
		SwingWorker<Object,Object> worker = new SwingWorker<Object, Object>() {
			protected Object doInBackground() throws Exception {
				view.getReportModel().reset();
				view.getReportModel().setPriceThreshold(AppCtx.getPersistentInt("optimiser.threshold.balance"));
				view.getReportModel().setWinPercentageThreshold(AppCtx.getPersistentInt("optimiser.threshold.winPercentage"));
				executor.run(candles, view.getParameters(), OptimiserController.this, OptimiserController.this);
				return null;
			}
		};
		worker.execute();
	}

    public void setStrategyClass(Class<?> strategyClass)
    {
        view.setStrategyClass(strategyClass);
    }
}

