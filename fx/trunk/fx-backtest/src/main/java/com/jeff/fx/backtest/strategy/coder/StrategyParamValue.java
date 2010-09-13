package com.jeff.fx.backtest.strategy.coder;

public class StrategyParamValue {

	private StrategyParam param;
	private Object value;

	public StrategyParamValue(StrategyParam param, Object value) {
		this.param = param;
		this.value = value;
	}

	public StrategyParam getParam() {
		return param;
	}

	public Object getValue() {
		return value;
	}
	
	public String toString() {
		return param + " = " + value;
	}
}
