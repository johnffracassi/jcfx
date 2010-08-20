package com.jeff.fx.backtest.orderbook.balancechart;

import org.apache.log4j.Logger;

import com.jeff.fx.backtest.engine.OrderBook;

public class BalanceChartController {
	
	private static Logger log = Logger.getLogger(BalanceChartController.class);

	private BalanceChartView view;
	private BalanceChartModel model;

	public BalanceChartController() {
		log.debug("created balance chart controller");
		view = new BalanceChartView();
		model = new BalanceChartModel();
	}

	public void update(OrderBook book) {
		model.update(book);
		view.update(model);
	}
	
	public BalanceChartView getView() {
		return view;
	}
	
	public BalanceChartModel getModel() {
		return model;
	}
}
