package com.jeff.fx.backtest.strategy.optimiser;

import com.jeff.fx.common.CandleCollection;

public interface OptimiserExecutor {
	public void run(CandleCollection candles, OptimiserView view);
	public void pause();
	public void resume();
	public void stop();
	public void reset();
}
