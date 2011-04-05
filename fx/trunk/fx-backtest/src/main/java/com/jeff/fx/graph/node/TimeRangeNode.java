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

        this.start = new TimeOfWeek(TimeOfWeek.MONDAY, 10, 30);
        this.end = new TimeOfWeek(TimeOfWeek.MONDAY, 11, 30);
    }

    @Override
    public boolean evaluate(CandleDataPoint candle, CandleFilterModel model)
    {
        TimeOfWeek time = new TimeOfWeek(candle.getDateTime());
        return time.isAfterOrEqualTo(start) && time.isBefore(end);
    }

    @Override
    public String getLabel() {
        return "Between\n" + start + " and\n" + end;
    }
}
