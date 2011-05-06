package com.jeff.fx.backtest.strategy.optimiser.param;

import com.jeff.fx.common.OfferSide;
import org.springframework.stereotype.Component;

@Component
public class OPOfferSide extends OptimiserParameter<OfferSide> {

    public OPOfferSide()
    {
        setStart(0);
        setMin(0);
        setEnd(OfferSide.values().length - 1);
        setMax(OfferSide.values().length - 1);
    }

	public OfferSide fromString(String val)
    {
		return OfferSide.valueOf(val);
	}

    @Override
    public OfferSide fromDouble(double val)
    {
        return OfferSide.values()[(int)val];
    }

    @Override
    public String toString(OfferSide val)
    {
        return val.toString();
    }

    @Override
    public double toDouble(OfferSide val)
    {
        return val.ordinal();
    }

    public Class<?>[] registerFor()
    {
        return new Class<?>[] { OfferSide.class };
    }
}

