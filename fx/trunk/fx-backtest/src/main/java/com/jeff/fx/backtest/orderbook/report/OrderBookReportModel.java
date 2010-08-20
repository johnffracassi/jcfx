package com.jeff.fx.backtest.orderbook.report;

import com.jeff.fx.backtest.engine.OrderBook;


public class OrderBookReportModel {

	private OrderBook data;
	
	public OrderBookReportModel() {
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
