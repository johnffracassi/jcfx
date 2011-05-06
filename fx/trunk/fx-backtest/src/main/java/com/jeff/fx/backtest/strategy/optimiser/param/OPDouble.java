package com.jeff.fx.backtest.strategy.optimiser.param;

import org.springframework.stereotype.Component;

@Component
public class OPDouble extends OptimiserParameter<Double> {

    public OPDouble() {}
    
	public Double fromString(String val) {
		if(val == null || val.trim().isEmpty()) {
			return 0.0;
		} else {
			return new Double(val);
		}
	}

    @Override
    public Double fromDouble(double val)
    {
        return val;
    }

    @Override
    public String toString(Double val)
    {
        return val.toString();
    }

    @Override
    public double toDouble(Double val)
    {
        return val;
    }

    @Override
    public Class<?>[] registerFor()
    {
        return new Class<?>[] { Double.class, Float.class, double.class, float.class };
    }
}

