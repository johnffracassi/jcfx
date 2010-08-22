package com.jeff.fx.backtest.strategy.optimiser;

import java.util.List;

import com.jeff.fx.backtest.strategy.ExecutorJobListener;
import com.jeff.fx.backtest.strategy.ExecutorStatusListener;
import com.jeff.fx.common.CandleCollection;

public interface OptimiserExecutor {
	public void run(CandleCollection candles, List<OptimiserParameter<?,?>> params, ExecutorJobListener jobListener, ExecutorStatusListener statusListener);
	public void pause();
	public void resume();
	public void stop();
	public void reset();
}
