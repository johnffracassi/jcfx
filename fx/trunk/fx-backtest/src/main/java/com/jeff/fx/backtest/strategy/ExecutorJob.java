package com.jeff.fx.backtest.strategy;

public class ExecutorJob {
	
	public int id;
	public int startIdx;
	public int endIdx;
	public long startTime;
	public long endTime;
	
	public ExecutorJob(int id, int startIdx, int endIdx) {
		super();
		this.id = id;
		this.startIdx = startIdx;
		this.endIdx = endIdx;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public int getStartIdx() {
		return startIdx;
	}

	public int getEndIdx() {
		return endIdx;
	}
}
