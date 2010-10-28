package com.jeff.fx.backtest.engine;

public class OrderBookReport {

	private int orderCount = 0;
	private double balance = 0;
	private double minBalance = 0;
	private double maxBalance = 0;
	private int wins = 0;
	private int losses = 0;
	private int stops = 0;
	private int takes = 0;
	private double high = Double.MIN_VALUE;
	private double low = Double.MAX_VALUE;
	private double fitness = Double.NaN;
	
	public OrderBookReport(OrderBook book) {
		analyse(book);
	}

	private void analyse(OrderBook book) {

		orderCount = book.getClosedOrders().size();
		
		balance = 0.0;
		
		for(BTOrder order : book.getClosedOrders()) {
			
			double profit = order.getProfit();
			balance += profit;
			
			if(profit > 0) wins ++;			
			if(profit < 0) losses ++;
			
			if(profit > high) high = profit;
			if(profit < low) low = profit;
			
			if(order.getCloseType() == OrderCloseType.StopLoss) stops ++;
			if(order.getCloseType() == OrderCloseType.TakeProfit) takes ++;
			
			if(balance < minBalance) minBalance = balance;
			if(balance > maxBalance) maxBalance = balance;
		}
		
		if(orderCount > 0)
		{ 
		    fitness = Math.pow((balance * balance) * (getWinPercentage() * getWinPercentage() / 10000.0 + 0.125) / orderCount, 1.0 / 3.0);
		}
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

	public int getTakeProfits() {
		return takes;
	}
	
	public int getStopLosses() {
		return stops;
	}

    public double getFitness()
    {
        return fitness;
    }

    public void setFitness(double fitness)
    {
        this.fitness = fitness;
    }
}
