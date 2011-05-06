package com.jeff.fx.backtest.strategy.optimiser.param;

import com.jeff.fx.common.TimeOfWeek;
import org.springframework.stereotype.Component;

@Component
public class OPTimeOfWeek extends OptimiserParameter<TimeOfWeek> {

    public OPTimeOfWeek() {}

	public TimeOfWeek fromString(String val)
    {
		if(val == null || val.trim().isEmpty())
        {
			return new TimeOfWeek(0);
		}
        else
        {
			return new TimeOfWeek(val);
		}
	}

    @Override
    public TimeOfWeek fromDouble(double val)
    {
        return new TimeOfWeek((int)val);
    }

    @Override
    public String toString(TimeOfWeek val)
    {
        return val.toString();
    }

    @Override
    public double toDouble(TimeOfWeek val)
    {
        return val.getMinuteOfWeek();
    }

    @Override
    public Class<?>[] registerFor()
    {
        return new Class<?>[] { TimeOfWeek.class };
    }
}
