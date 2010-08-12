package com.jeff.fx.backtest.strategy.optimiser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingWorker;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.strategy.MultiThreadedExecutor;
import com.jeff.fx.backtest.strategy.time.StrategyView;
import com.jeff.fx.common.CandleCollection;

public class OptimiserController {

	private ExecutorParametersController epController = new ExecutorParametersController();
	private CandleCollection candles;
	private OptimiserView view;
	private OptimiserExecutor executor = new MultiThreadedExecutor();
	
	public OptimiserController(final StrategyView parent) {
		
		view = new OptimiserView();
		epController.setView(view.getPnlExecutorParameters());
		
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
	
	public void runOptimiser() {
		SwingWorker<Object,Object> worker = new SwingWorker<Object, Object>() {
			protected Object doInBackground() throws Exception {
				view.getReportModel().reset();
				view.getReportModel().setPriceThreshold(AppCtx.getPersistentInt("optimiser.threshold.balance"));
				view.getReportModel().setWinPercentageThreshold(AppCtx.getPersistentInt("optimiser.threshold.winPercentage"));
				executor.run(candles, view);
				return null;
			}
		};
		worker.execute();
	}
}

