package com.jeff.fx.backtest.strategy.time;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingWorker;

import com.jeff.fx.common.CandleCollection;

public class OptimiserController {

	private CandleCollection candles;
	private OptimiserView view;

	public OptimiserController(final StrategyView parent) {
		
		view = new OptimiserView();
		
		view.getRunButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runOptimiser();
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
	
	public void runOptimiser() {
		SwingWorker<Object,Object> worker = new SwingWorker<Object, Object>() {
			protected Object doInBackground() throws Exception {
				optimiserWorker();
				return null;
			}
		};
		worker.execute();
	}
	
	private void optimiserWorker() {
		
		view.getReportModel().reset();
		
		OptimiserExecutor executor = new MultiThreadedExecutor();
		executor.run(candles, view);
	}
}

