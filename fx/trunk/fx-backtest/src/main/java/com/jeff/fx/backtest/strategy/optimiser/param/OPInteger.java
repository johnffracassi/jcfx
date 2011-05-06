package com.jeff.fx.backtest.strategy.optimiser.param;

import org.springframework.stereotype.Component;

@Component
public class OPInteger extends OptimiserParameter<Integer>
{
    public OPInteger() {}

	public Integer fromString(String val) {
		if(val == null || val.trim().isEmpty()) {
			return 0;
		} else {
			return new Integer(val);
		}
	}

    @Override
    public Integer fromDouble(double val)
    {
        return (int)val;
    }

    @Override
    public String toString(Integer val)
    {
        return String.valueOf(val);
    }

    @Override
    public double toDouble(Integer val)
    {
        return val.doubleValue();
    }

    @Override
    public Class<?>[] registerFor()
    {
        return new Class<?>[] { Integer.class, int.class };
    }
}
