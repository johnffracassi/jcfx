package com.jeff.fx.backtest.strategy.coder;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

public class StrategyCodeModel {

	private String className;
	private List<StrategyParam> params;
	private String openCode;
	private String closeCode;

	public StrategyCodeModel() {
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<StrategyParam> getParams() {
		return params;
	}

	public void setParams(List<StrategyParam> params) {
		this.params = params;
	}

	public String getOpenCode() {
		return openCode;
	}

	public void setOpenCode(String openCode) {
		this.openCode = openCode;
	}

	public String getCloseCode() {
		return closeCode;
	}

	public void setCloseCode(String closeCode) {
		this.closeCode = closeCode;
	}
}
