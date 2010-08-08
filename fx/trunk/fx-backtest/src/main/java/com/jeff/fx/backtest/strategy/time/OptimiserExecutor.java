package com.jeff.fx.backtest.strategy.time;

import com.jeff.fx.common.CandleCollection;

public interface OptimiserExecutor {
	public void run(CandleCollection candles, OptimiserView view);
}
