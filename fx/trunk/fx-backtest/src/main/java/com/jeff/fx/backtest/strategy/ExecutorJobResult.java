package com.jeff.fx.backtest.strategy;

import java.io.Serializable;
import java.util.List;

public class ExecutorJobResult implements Serializable {

	private static final long serialVersionUID = 3837365835231965741L;

	private ExecutorJob job;
	private List<ExecutorTaskResult> taskResults;
	
	public ExecutorJobResult(ExecutorJob job, List<ExecutorTaskResult> taskResults) {
		this.job = job;
		this.taskResults = taskResults;
	}
	
	public ExecutorJob getJob() {
		return job;
	}
	
	public List<ExecutorTaskResult> getTaskResults() {
		return taskResults;
	}
}
