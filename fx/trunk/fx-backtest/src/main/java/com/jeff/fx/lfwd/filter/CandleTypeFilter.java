package com.jeff.fx.lfwd.filter;

import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.candletype.CandleType;

public class CandleTypeFilter implements SimpleCandleFilter
{
    private CandleType type;
    private int offset = 0;

    public CandleTypeFilter(CandleType type, int offset)
    {
        this.type = type;
        this.offset = offset;
    }

    @Override
    public boolean filter(CandleFilterModel model)
    {
        int idx = model.getIndex();
        if(idx - offset > 0)
            return !type.is(model.getCandles().getCandle(idx - offset));
        else
            return false;
    }
}
