package com.jeff.fx.backtest.orderbook;

import com.jeff.fx.backtest.engine.BTOrder;

public interface OrderSelectionListener {
	void orderSelected(BTOrder order);
}
