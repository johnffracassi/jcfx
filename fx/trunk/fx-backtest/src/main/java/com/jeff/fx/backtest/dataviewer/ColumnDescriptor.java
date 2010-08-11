package com.jeff.fx.backtest.dataviewer;

public class ColumnDescriptor {
	
	private String name;
	private Class<?> type;

	public ColumnDescriptor(String name, Class<?> type) {
		this.name = name;
		this.type = type;
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
	
	public void setType(Class<?> clazz) {
		this.type = clazz;
	}
}