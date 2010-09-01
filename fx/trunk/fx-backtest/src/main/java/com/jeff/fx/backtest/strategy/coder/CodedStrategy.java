package com.jeff.fx.backtest.strategy.coder;

import java.util.Map;

import org.apache.log4j.Logger;

import com.jeff.fx.backtest.engine.AbstractIncrementalStrategy;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;

public abstract class CodedStrategy extends AbstractIncrementalStrategy {
	
	private static Logger log = Logger.getLogger(CodedStrategy.class);
	
	public CodedStrategy() {
		super(-1);
	}
	
	public abstract String getName();
	public abstract void setup(Map<String, Object> params, IndicatorCache indicators, CandleCollection candles);
	public abstract boolean open(CandleDataPoint candle, int idx) throws Exception;
	public abstract boolean close(CandleDataPoint candle, int idx) throws Exception;
	
	public void candle(CandleDataPoint candle, int idx) {
		try {
			close(candle, idx);
			open(candle, idx);
		} catch(Exception ex) {
			// TODO needs to be reported to user log or something?
			log.error("error while executing coded strategy", ex);
			stop();
		}
	}

	public boolean isTestValid() {
		return true;
	}
}
