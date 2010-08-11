package com.jeff.fx.backtest.strategy.orderbook;

import com.jeff.fx.backtest.engine.OrderBook;


public class OrderBookModel {

	private OrderBook data;
	
	public OrderBookModel() {
		data = new OrderBook();
	}
	
	public void update(OrderBook ob) {
		this.data = ob;
	}
	
	public int getOrderCount() {
		return data.getClosedOrders().size();
	}
	
	public OrderBook getOrderBook() {
		return data;
	}
}
