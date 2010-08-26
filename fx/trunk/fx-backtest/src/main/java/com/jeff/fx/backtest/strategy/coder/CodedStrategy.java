package com.jeff.fx.backtest.strategy.coder;

import java.util.Map;

import com.jeff.fx.backtest.engine.AbstractIncrementalStrategy;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleDataPoint;

public abstract class CodedStrategy extends AbstractIncrementalStrategy {
	
	protected IndicatorCache indicators;
	protected Map<String, Object> param;

	public CodedStrategy() {
		super(-1);
	}
	
	public void candle(CandleDataPoint candle) {
		close(candle, param, indicators);
		open(candle, param, indicators);
	}

	public abstract String getName();
	public abstract boolean open(CandleDataPoint candle, Map<String, Object> param, IndicatorCache indicators);
	public abstract boolean close(CandleDataPoint candle, Map<String, Object> param, IndicatorCache indicators);
	
	public boolean isTestValid() {
		return true;
	}

	public IndicatorCache getIndicators() {
		return indicators;
	}

	public void setIndicators(IndicatorCache indicators) {
		this.indicators = indicators;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
}
