package com.jeff.fx.backtest.strategy.coder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="parameter")
public class StrategyParam implements Comparable<StrategyParam> {

	private String name;
	private Class<?> type;
	private String description;
	private String label;
	private int ordinal;
	private String getter;
	private String setter;
	
	public StrategyParam() {
		this("N/A", String.class);
	}
	
	public StrategyParam(String name, Class<?> type) {
		this.name = name;
		this.type = type;
		this.description = name;
		this.label = name;
		this.getter = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
		this.setter = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	public int compareTo(StrategyParam thatObj) {
		StrategyParam that = (StrategyParam)thatObj;
		if(this.ordinal < that.ordinal) return -1;
		if(this.ordinal > that.ordinal) return 1;
		return this.name.compareTo(that.name);
	}
	
	// @todo implement to/from string
	public String toString() {
		return "[" + name + " / " + label + " / " + type.getSimpleName() + " / " + description + "]";
	}
	
	// @todo implement to/from string
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getSetter() {
		return setter;
	}

	public void setSetter(String setter) {
		this.setter = setter;
	}
}
