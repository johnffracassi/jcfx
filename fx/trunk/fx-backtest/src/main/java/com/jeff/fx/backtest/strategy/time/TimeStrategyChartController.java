package com.jeff.fx.backtest.strategy.time;

import org.apache.log4j.Logger;

import com.jeff.fx.backtest.engine.OrderBook;

public class TimeStrategyChartController {
	
	private static Logger log = Logger.getLogger(TimeStrategyChartController.class);

	private TimeStrategyChartView view = new TimeStrategyChartView();
	private TimeStrategyChartModel model = new TimeStrategyChartModel();

	public TimeStrategyChartController() {
	}

	public void update(OrderBook book) {
		model.update(book);
		view.update(model);
	}
	
	public TimeStrategyChartView getView() {
		return view;
	}
	
	public TimeStrategyChartModel getModel() {
		return model;
	}
}
