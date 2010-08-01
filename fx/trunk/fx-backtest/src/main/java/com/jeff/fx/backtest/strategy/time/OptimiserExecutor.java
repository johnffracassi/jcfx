package com.jeff.fx.backtest.strategy.time;

import java.util.List;

import com.jeff.fx.common.CandleDataPoint;

public interface OptimiserExecutor {
	public void run(List<CandleDataPoint> candles, OptimiserView view);
}
