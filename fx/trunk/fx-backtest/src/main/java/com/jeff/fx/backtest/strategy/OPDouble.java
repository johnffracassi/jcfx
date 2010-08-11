package com.jeff.fx.backtest.strategy;

import com.jeff.fx.backtest.strategy.optimiser.OptimiserParameter;

public class OPDouble extends OptimiserParameter<Double,Double> {

	public OPDouble(String arg0, double arg1, double arg2, double arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public Double fromString(String val) {
		if(val == null || val.trim().isEmpty()) {
			return 0.0;
		} else {
			return new Double(val);
		}
	}

	public int getStepCount() {
		return (int)((getEnd() - getStart()) / getStep());
	}
	
	public Double getValue(int idx) {
		return getStart() + (getStep() * idx);
	}
}

