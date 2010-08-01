package com.jeff.fx.backtest.strategy.time;

import java.util.Map;

import com.jeff.fx.backtest.engine.OrderBookReport;

public class OptimiserReportRow {

	private int id;
	private OrderBookReport report;
	private Map<String, Object> params;

	public OptimiserReportRow(int id, OrderBookReport report, Map<String, Object> params) {
		super();
		this.id = id;
		this.report = report;
		this.params = params;
	}

	public OrderBookReport getBookReport() {
		return report;
	}

	public void setBookReport(OrderBookReport report) {
		this.report = report;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public int getId() {
		return id;
	}
}
