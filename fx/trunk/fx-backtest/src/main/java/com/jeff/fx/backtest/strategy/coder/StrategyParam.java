package com.jeff.fx.backtest.strategy.coder;

public class StrategyParam {

	private String name;
	private Class<?> type;
	
	public StrategyParam(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}

	public String toString() {
		return super.toString();
	}
	
	public Object fromString() {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}
}