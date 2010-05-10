package com.jeff.fx.backtest.engine;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.OfferSide;

public class AbstractStrategy {
	
	private int id = -1;
	
	private double balance = 0.0;
	private int wins = 0;
	private int losses = 0;

	private OrderBook orderBook;

	public AbstractStrategy(int id) {
		this.id = id;
		orderBook = new OrderBook();
	}
	
	protected void open(BTOrder order, CandleDataPoint candle) {
		order.setOpenTime(candle.getDate());
		order.setOpenPrice(order.getOfferSide() == OfferSide.Ask ? candle.getBuyOpen() : candle.getSellOpen());
		orderBook.post(order);
	}

	protected void close(CandleDataPoint candle) {
		
		if(orderBook.hasOpenOrders()) {
			
			BTOrder currentOrder = orderBook.getOpenOrders().get(0);
			currentOrder.setCloseTime(candle.getDate());
			currentOrder.setClosePrice(currentOrder.getOfferSide() == OfferSide.Ask ? candle.getSellOpen() : candle.getBuyOpen());

			double profit = 10000.0 * (currentOrder.getOpenPrice() - currentOrder.getClosePrice());
			
			if(profit > 0.0) {
				wins ++;
			} else { 
				losses ++;
			}

			balance += profit;
			
			orderBook.close(currentOrder);
			
			currentOrder = null;
		}
	}
	
	public boolean hasOpenOrder() {
		return orderBook.hasOpenOrders();
	}
	
	public double getBalance() {
		return balance;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public int getId() {
		return id;
	}
}
