package com.jeff.fx.backtest.strategy.time;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

import com.jeff.fx.backtest.engine.AbstractStrategy;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.BTParameterSet;
import com.jeff.fx.backtest.engine.BTParameterTable;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.OfferSide;

public class TimeStrategy extends AbstractStrategy {
	
	private int openAtDay = 0;
	private LocalTime openAtTime = null;
	private int closeAtDay = 0;
	private LocalTime closeAtTime = null;
	private int stopLoss = 0;
	private int takeProfit = 0;
	
	public TimeStrategy(int id, int openAtDay, LocalTime openAtTime, int closeAtDay, LocalTime closeAtTime) {
		
		super(id);
		
		this.openAtDay = openAtDay;
		this.openAtTime = openAtTime;
		this.closeAtDay = closeAtDay;
		this.closeAtTime = closeAtTime;
	}
	
	public static List<TimeStrategy> createTestSet(BTParameterSet ps) {
		
		double[][] parameters = BTParameterTable.getParameterValueTable(ps);
		int permutations = parameters[0].length;
		
		List<TimeStrategy> list = new ArrayList<TimeStrategy>(permutations);
		for(int i=0; i<permutations; i++) {
//			TimeStrategy strategy = new TimeStrategy(i+1, new double[] { parameters[0][i], parameters[1][i] });
//			list.add(strategy);
		}
		
		return list;
	}
	
	public void candle(CandleDataPoint candle) {
		
		// check all orders for a stop loss or take profit
		List<BTOrder> ordersToClose = new ArrayList<BTOrder>();
		for(BTOrder order : getOrderBook().getOpenOrders()) {
			if(isOrderStopped(order, candle)) {
				ordersToClose.add(order);
			}
		}
		
		// close the orders (do it outside the loop to avoid concurrent modification)
		for(BTOrder order : ordersToClose) {
			close(order, candle);
		}
		
		// is it open/close time?
		int dayOfWeek = candle.getDate().getDayOfWeek();
		LocalTime time = candle.getDate().toLocalTime();
		if(openAtDay == dayOfWeek && time.getHourOfDay() == openAtTime.getHourOfDay() && time.getMinuteOfHour() == openAtTime.getMinuteOfHour() && !hasOpenOrder()) {
			BTOrder order = new BTOrder();
			order.setOfferSide(OfferSide.Ask);
			order.setUnits(1.0);
			
			if(takeProfit > 0) {
				order.setTakeProfit(takeProfit);
			}
			
			if(stopLoss > 0) {
				order.setStopLoss(stopLoss);
			}
			
			open(order, candle);
		} else if(closeAtDay == dayOfWeek && time.getHourOfDay() == closeAtTime.getHourOfDay() && time.getMinuteOfHour() == closeAtTime.getMinuteOfHour() && hasOpenOrder()) {
			close(getOrderBook().getOpenOrders().get(0), candle);
		} 
	}

	public int getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(int stopLoss) {
		this.stopLoss = stopLoss;
	}

	public int getTakeProfit() {
		return takeProfit;
	}

	public void setTakeProfit(int takeProfit) {
		this.takeProfit = takeProfit;
	}
}
