package com.jeff.fx.graph.node;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.gui.beanform.Label;
import com.jeff.fx.gui.beanform.Property;
import com.jeff.fx.lookforward.CandleFilterModel;

@Label("Time Range Node")
public class TimeRangeNode extends BaseNode
{
    @Property(key="from")
    @Label("From")
    private TimeOfWeek from;

    @Property(key="from.inclusive")
    @Label("Inclusive (from)")
    private boolean fromInclusive;

    @Property(key="to")
    @Label("To")
    private TimeOfWeek to;

    @Property(key="to.inclusive")
    @Label("Inclusive (to)")
    private boolean toInclusive;

    public TimeRangeNode()
    {
        super("Time Range");

        this.from = new TimeOfWeek(TimeOfWeek.MONDAY, 10, 30);
        this.fromInclusive = true;
        this.to = new TimeOfWeek(TimeOfWeek.MONDAY, 11, 30);
        this.toInclusive = false;
    }

    @Override
    public boolean evaluate(CandleDataPoint candle, CandleFilterModel model)
    {
        TimeOfWeek time = new TimeOfWeek(candle.getDateTime());
        return time.isAfterOrEqualTo(from) && time.isBefore(to);
    }

    @Override
    public String toString()
    {
        return "Between\n" + from + (fromInclusive?"*":"") + " and\n" + to + (toInclusive?"*":"");
    }

    public TimeOfWeek getFrom()
    {
        return from;
    }

    public void setFrom(TimeOfWeek from)
    {
        this.from = from;
    }

    public boolean getFromInclusive()
    {
        return fromInclusive;
    }

    public void setFromInclusive(boolean fromInclusive)
    {
        this.fromInclusive = fromInclusive;
    }

    public TimeOfWeek getTo()
    {
        return to;
    }

    public void setTo(TimeOfWeek to)
    {
        this.to = to;
    }

    public boolean getToInclusive()
    {
        return toInclusive;
    }

    public void setToInclusive(boolean toInclusive)
    {
        this.toInclusive = toInclusive;
    }
}
