package com.jeff.fx.backtest.strategy.decorator;

import com.jeff.fx.backtest.engine.AbstractIncrementalStrategy;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.common.CandleDataPoint;

public class StopLoss extends AbstractStrategyDecorator {

	private int distance;
	
	public StopLoss(AbstractIncrementalStrategy client, int distance) {
		super(client);
		this.distance = distance;
	}

	@Override
	protected void openOrder(BTOrder order, CandleDataPoint candle) {
		order.setStopLoss(distance); 
		super.openOrder(order, candle);
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
}
