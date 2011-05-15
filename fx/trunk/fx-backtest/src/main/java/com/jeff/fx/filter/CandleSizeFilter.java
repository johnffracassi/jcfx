package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;

public class CandleSizeFilter implements SimpleCandleFilter
{
    private int size;

    public CandleSizeFilter(int size)
    {
        this.size = size;
    }

    @Override
    public boolean filter(CandleFilterModel model)
    {
        CandleDataPoint candle = model.getCandles().getCandle(model.getIndex());
        return size > 0 ? candle.getSize() < size : candle.getSize() > size;
    }
}
