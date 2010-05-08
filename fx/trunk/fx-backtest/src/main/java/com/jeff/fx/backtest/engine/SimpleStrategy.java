package com.jeff.fx.backtest.engine;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.OfferSide;

public class SimpleStrategy {
	
	private BTOrder currentOrder = null;

	private int stayClosedFor = 20;
	private int stayOpenFor = 20;
	private int candleCount = 0;
	private int totalCandleCount = 0;
	
	public SimpleStrategy(double[] params) {
		stayClosedFor = (int)params[0];
		stayOpenFor = (int)params[1];
		System.out.println("creating SimpleStrategy with stayOpenFor=" + stayOpenFor + ",stayClosedFor=" + stayClosedFor);
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
			System.out.println("closed order #" + order.getId() + " @ " + order.getCloseTime() + " (" + totalCandleCount + "): " + 10000*(order.getOpenPrice() - order.getClosePrice()));
			currentOrder = null;
		}
	}
}
