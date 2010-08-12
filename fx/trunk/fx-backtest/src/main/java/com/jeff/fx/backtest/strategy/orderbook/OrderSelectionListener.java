package com.jeff.fx.backtest.strategy.orderbook;

import com.jeff.fx.backtest.engine.BTOrder;

public interface OrderSelectionListener {
	public void orderSelected(BTOrder order);
}
