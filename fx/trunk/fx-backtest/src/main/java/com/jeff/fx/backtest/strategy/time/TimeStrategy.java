package com.jeff.fx.backtest.strategy.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jeff.fx.backtest.engine.AbstractStrategy;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.OfferSide;
import com.jeff.fx.common.TimeOfWeek;

public class TimeStrategy extends AbstractStrategy {
	
	private static Logger log = Logger.getLogger(TimeStrategy.class);

	private TimeOfWeek open = null;
	private TimeOfWeek close = null;
	private int stopLoss = 0;
	private int takeProfit = 0;
	
	public TimeStrategy(int id, Map<String,Object> parameters) {
		
		super(id);
		
		stopLoss = (Integer)parameters.get("stopLoss");
		takeProfit = (Integer)parameters.get("takeProfit");
		open = (TimeOfWeek)parameters.get("open");
		close = (TimeOfWeek)parameters.get("close");
	}
	
	public boolean validate() {
		return (!open.equals(close));
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
		TimeOfWeek tow = new TimeOfWeek(candle.getDate().getDayOfWeek(), candle.getDate().toLocalTime());
		if(tow.equals(open) && !hasOpenOrder()) {
			BTOrder order = new BTOrder();
			order.setOfferSide(OfferSide.Ask);
			order.setUnits(1.0);
			
			if(takeProfit > 0) {
				order.setTakeProfit(takeProfit);
			}
			
			if(stopLoss > 0) {
				order.setStopLoss(stopLoss);
			}
			
			openOrder(order, candle);
			
		} else if(tow.equals(close) && hasOpenOrder()) {
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

	public TimeOfWeek getOpen() {
		return open;
	}

	public void setOpen(TimeOfWeek open) {
		this.open = open;
	}

	public TimeOfWeek getClose() {
		return close;
	}

	public void setClose(TimeOfWeek close) {
		this.close = close;
	}
}
