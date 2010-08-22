package com.jeff.fx.backtest.strategy;

import java.io.Serializable;
import java.util.Map;

import com.jeff.fx.backtest.engine.OrderBookReport;

public class ExecutorTaskResult implements Serializable {

	private static final long serialVersionUID = 8434921015068658076L;
	
	private int taskId;
	private OrderBookReport report;
	private Map<String, Object> params;

	public ExecutorTaskResult(int taskId, OrderBookReport report, Map<String, Object> params) {
		this.taskId = taskId;
		this.report = report;
		this.params = params;
	}

	public int getId() {
		return taskId;
	}
	
	public OrderBookReport getBookReport() {
		return report;
	}

	public Map<String, Object> getParams() {
		return params;
	}

}
