package com.jeff.fx.backtest.strategy.optimiser;

public abstract class OptimiserParameter<T,S> {
	
	private String key;
	private T start;
	private T end;
	private S step;
	
	public abstract T fromString(String val);
	public abstract int getStepCount();
	public abstract T getValue(int step);
	
	public OptimiserParameter(String key, T start, T end, S step) {
		super();
		this.key = key;
		this.start = start;
		this.end = end;
		this.step = step;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public T getStart() {
		return start;
	}

	public void setStart(T start) {
		this.start = start;
	}

	public T getEnd() {
		return end;
	}

	public void setEnd(T end) {
		this.end = end;
	}

	public S getStep() {
		return step;
	}

	public void setStep(S step) {
		this.step = step;
	}
}

