package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;

public class CandleRangeFilter implements SimpleCandleFilter
{
    private int size;

    public CandleRangeFilter(int size)
    {
        this.size = size;
    }

    @Override
    public boolean filter(CandleFilterModel model)
    {
        CandleDataPoint candle = model.getCandles().getCandle(model.getIndex());
        return candle.getRange() < size;
    }
}
