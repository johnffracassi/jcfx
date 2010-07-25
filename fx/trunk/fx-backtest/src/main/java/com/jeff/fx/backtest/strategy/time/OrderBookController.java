package com.jeff.fx.backtest.strategy.time;

import com.jeff.fx.backtest.engine.OrderBook;


public class OrderBookController {

	private OrderBookModel model = new OrderBookModel();
	private OrderBookView view = new OrderBookView();
	
	public OrderBookController() {
	}
	
	public OrderBookView getView() {
		return view;
	}

	public void update(OrderBook book) {
		model.update(book);
		view.update(model);
	}
}
