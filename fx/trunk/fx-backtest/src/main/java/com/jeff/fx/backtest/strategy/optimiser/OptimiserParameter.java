package com.jeff.fx.backtest.strategy.optimiser;

public abstract class OptimiserParameter<ValueType,StepType> {
	
	private String key;
	private ValueType start;
	private ValueType end;
	private StepType step;
	
	public abstract ValueType fromString(String val);
	public abstract int getStepCount();
	public abstract ValueType getValue(int step);
	
	public OptimiserParameter(String key, ValueType start, ValueType end, StepType step) {
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

	public ValueType getStart() {
		return start;
	}

	public void setStart(ValueType start) {
		this.start = start;
	}

	public ValueType getEnd() {
		return end;
	}

	public void setEnd(ValueType end) {
		this.end = end;
	}

	public StepType getStep() {
		return step;
	}

	public void setStep(StepType step) {
		this.step = step;
	}
}

