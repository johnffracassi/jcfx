package com.jeff.fx.filter;

import com.jeff.fx.filter.candletype.CandleType;

public class CandlePatternFilter implements SimpleCandleFilter
{
    private CandleType type;
    private int offset = 0;

    public CandlePatternFilter(CandleType type, int offset)
    {
        this.type = type;
        this.offset = offset;
    }

    @Override
    public boolean filter(CandleFilterModel model)
    {
        int idx = model.getIndex();
        if(idx - offset > 0)
            return !type.evaluate(model.getCandles().getCandle(idx - offset));
        else
            return false;
    }
}
