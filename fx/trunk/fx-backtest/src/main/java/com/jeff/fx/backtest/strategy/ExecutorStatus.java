package com.jeff.fx.backtest.strategy;

public class ExecutorStatus {

	private long elapsedTime;
	private int totalTaskCount;
	private int tasksExecuted;
	private int jobsExecuted;
	private int totalJobCount;
	
	public ExecutorStatus(long elapsedTime, int totalTaskCount, int tasksExecuted, int jobsExecuted, int totalJobCount) {
		this.elapsedTime = elapsedTime;
		this.totalTaskCount = totalTaskCount;
		this.tasksExecuted = tasksExecuted;
		this.jobsExecuted = jobsExecuted;
		this.totalJobCount = totalJobCount;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public int getTotalTaskCount() {
		return totalTaskCount;
	}

	public int getTasksExecuted() {
		return tasksExecuted;
	}

	public int getJobsExecuted() {
		return jobsExecuted;
	}

	public int getTotalJobCount() {
		return totalJobCount;
	}

}
