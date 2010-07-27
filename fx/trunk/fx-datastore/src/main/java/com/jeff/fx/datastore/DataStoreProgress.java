package com.jeff.fx.datastore;

public class DataStoreProgress {
	
	private int steps = 0;
	private int progress = 0;

	public DataStoreProgress(int progress, int steps) {
		this.steps = steps;
		this.progress = progress;
	}
	
	public double getPercentage() {
		if(steps == 0) {
			return 0.0;
		} else {
			return (double)progress / (double)steps * 100.0;
		}
	}
	
	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

}
