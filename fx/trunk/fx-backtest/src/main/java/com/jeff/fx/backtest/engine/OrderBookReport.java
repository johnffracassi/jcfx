package com.jeff.fx.backtest.engine;

public class OrderBookReport {

	private OrderBook book;
	
	private int orderCount = 0;
	private double balance = 0;
	private double minBalance = 0;
	private double maxBalance = 0;
	private int wins = 0;
	private int losses = 0;
	private double high = Double.MIN_VALUE;
	private double low = Double.MAX_VALUE;
	
	public OrderBookReport(OrderBook book) {
		this.book = book;
		analyse();
	}

	private void analyse() {

		orderCount = book.getClosedOrders().size();
		
		balance = 0.0;
		
		for(BTOrder order : book.getClosedOrders()) {
			
			double profit = order.getProfit();
			balance += profit;
			
			if(profit > 0) wins ++;			
			if(profit < 0) losses ++;
			
			if(profit > high) high = profit;
			if(profit < low) low = profit;
			
			if(balance < minBalance) minBalance = balance;
			if(balance > maxBalance) maxBalance = balance;
		}
	}

	public OrderBook getBook() {
		return book;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public int getWins() {
		return wins;
	}
	
	public double getWinPercentage() {
		if(orderCount == 0) return 0.0;
		return (double)wins / (double)orderCount * 100.0;
	}

	public double getLossPercentage() {
		if(orderCount == 0) return 0.0;
		return (double)losses / (double)orderCount * 100.0;
	}

	public int getLosses() {
		return losses;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getBalance() {
		return balance;
	}

	public double getMinBalance() {
		return minBalance;
	}

	public double getMaxBalance() {
		return maxBalance;
	}
}