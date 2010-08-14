package com.jeff.fx.backtest.strategy.optimiser;


public class OPInteger extends OptimiserParameter<Integer,Integer> {

	public OPInteger(String arg0, int arg1, int arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public Integer fromString(String val) {
		if(val == null || val.trim().isEmpty()) {
			return 0;
		} else {
			return new Integer(val);
		}
	}

	public int getStepCount() {
		return (getEnd() - getStart()) / getStep() + 1;
	}
	
	public Integer getValue(int idx) {
		return getStart() + (getStep() * idx);
	}
}
