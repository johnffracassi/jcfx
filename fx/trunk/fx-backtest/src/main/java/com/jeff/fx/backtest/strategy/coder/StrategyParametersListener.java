package com.jeff.fx.backtest.strategy.coder;

public interface StrategyParametersListener {

	void reset();
	void parameterAdded(StrategyParam param);
	void parameterRemoved(StrategyParam param);
	void parameterUpdated(StrategyParam param);
	
}
