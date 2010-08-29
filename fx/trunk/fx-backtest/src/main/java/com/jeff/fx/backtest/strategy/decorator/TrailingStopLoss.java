package com.jeff.fx.backtest.strategy.decorator;

import com.jeff.fx.backtest.engine.AbstractIncrementalStrategy;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.common.CandleDataPoint;

public class TrailingStopLoss extends StopLoss {

	public TrailingStopLoss(AbstractIncrementalStrategy client, int distance) {
		super(client, distance);
	}

	public void candle(CandleDataPoint candle) {
		
		if(hasOpenOrder()) {
			BTOrder order = getOrderBook().getOpenOrders().get(0);
			double openPrice = candle.getOpen();
			double currentSL = order.getStopLossPrice();
			double newSL = openPrice - getDistance();
			
			if(newSL > currentSL) {
				order.setStopLossPrice(newSL);
			}
		}
		
		super.candle(candle);
	}
}
