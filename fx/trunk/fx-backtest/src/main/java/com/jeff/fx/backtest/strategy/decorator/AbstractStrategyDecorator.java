package com.jeff.fx.backtest.strategy.decorator;

import com.jeff.fx.backtest.engine.AbstractIncrementalStrategy;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;

public class AbstractStrategyDecorator extends AbstractIncrementalStrategy {

	private AbstractIncrementalStrategy client;
	
	public AbstractStrategyDecorator(AbstractIncrementalStrategy client) {
		super(client.getId());
		this.client = client;
	}

	public void candle(CandleDataPoint candle, int idx) {
		client.candle(candle, idx);
	}

	public OrderBook execute(CandleCollection cc) {
		return client.execute(cc);
	}

	public boolean isTestValid() {
		return client.isTestValid();
	}

	public String toString() {
		return client.toString();
	}
}
