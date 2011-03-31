package com.jeff.fx.graph.node;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.lookforward.CandleFilterModel;

public class TimeRangeNode extends BaseNode
{
    private TimeOfWeek start;
    private TimeOfWeek end;

    public TimeRangeNode()
    {
        super("TimeRange");
    }

    @Override
    public boolean evaluate(CandleDataPoint candle, CandleFilterModel model)
    {
        System.out.println("Checking time range?");
        return (true);
    }
}
