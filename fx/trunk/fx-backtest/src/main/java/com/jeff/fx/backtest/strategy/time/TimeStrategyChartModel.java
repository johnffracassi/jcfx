package com.jeff.fx.backtest.strategy.time;

import java.util.List;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;

public class TimeStrategyChartModel {
	
	private OrderBook orderBook;

	protected void update(OrderBook ob) {
		this.orderBook = ob;
	}

	public OrderBook getOrderBook() {
		return orderBook;
	}
	
	public TimeSeries getChartData() {

		TimeSeries ts = new TimeSeries("Balance", Minute.class);

		List<BTOrder> orders = orderBook.getClosedOrders();

		double balance = 0.0;
		for (BTOrder order : orders) {
			balance += order.getProfit();
			ts.addOrUpdate(new Minute(order.getCloseTime().toDateTime().toDate()), balance);
		}

		return ts;
	}
}
