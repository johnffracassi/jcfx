package com.jeff.fx.backtest.engine;

import java.util.ArrayList;
import java.util.List;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.OfferSide;

public class SimpleStrategy {
	
	private BTOrder currentOrder = null;

	private int id = -1;
	
	private int stayClosedFor = 0;
	private int stayOpenFor = 0;
	private int candleCount = 0;
	private int totalCandleCount = 0;
	
	private double balance = 0.0;
	private int wins = 0;
	private int losses = 0;
	
	private SimpleStrategy(double[] params) {
		stayClosedFor = (int)params[0];
		stayOpenFor = (int)params[1];
		System.out.println("creating SimpleStrategy with stayOpenFor=" + stayOpenFor + ",stayClosedFor=" + stayClosedFor);
	}
	
	public static List<SimpleStrategy> createTestSet(BTParameterSet ps) {
		
		double[][] parameters = BTParameterTable.getParameterValueTable(ps);
		int permutations = parameters[0].length;
		
		List<SimpleStrategy> list = new ArrayList<SimpleStrategy>(permutations);
		for(int i=0; i<permutations; i++) {
			SimpleStrategy strategy = new SimpleStrategy(new double[] { parameters[0][i], parameters[1][i] });
			strategy.id = i+1;
			list.add(strategy);
		}
		
		return list;
	}
	
	public void candle(CandleDataPoint candle) {
		totalCandleCount ++;
		candleCount ++;
		
		if(currentOrder == null && candleCount == stayClosedFor) {
			open(new BTOrder(), candle);
			candleCount = 0;
		} else if(currentOrder != null && candleCount == stayOpenFor) {
			close(currentOrder, candle);
			candleCount = 0;
		}
	}
	
	private void open(BTOrder order, CandleDataPoint candle) {
		order.setOpenTime(candle.getDate());
		order.setOpenPrice(order.getOfferSide() == OfferSide.Ask ? candle.getBuyOpen() : candle.getSellOpen());
		order.setId(1);
		currentOrder = order;
	}

	private void close(BTOrder order, CandleDataPoint candle) {
		if(order != null) {
			
			order.setCloseTime(candle.getDate());
			order.setClosePrice(order.getOfferSide() == OfferSide.Ask ? candle.getSellOpen() : candle.getBuyOpen());

			double profit = 10000.0 * (order.getOpenPrice() - order.getClosePrice());
			
			balance += profit;
			
			if(profit > 0.0)
				wins ++;
			else 
				losses ++;

			currentOrder = null;
		}
	}

	@Override
	public String toString() {
		return id + ". " + stayOpenFor + "/" + stayClosedFor + " = " + balance + " (" + wins + "/" + losses + ")";
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
