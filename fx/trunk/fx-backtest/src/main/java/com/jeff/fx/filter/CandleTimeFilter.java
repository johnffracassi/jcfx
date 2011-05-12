package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.TimeOfWeek;

public class CandleTimeFilter implements SimpleCandleFilter
{
    private TimeOfWeek time;

    public CandleTimeFilter(TimeOfWeek time)
    {
        this.time = time;
    }

    @Override
    public boolean filter(CandleFilterModel model, int idx)
    {
        CandleDataPoint candle = model.getCandles().getCandle(idx);
        return new TimeOfWeek(candle.getDateTime()).equals(time);
    }
}
