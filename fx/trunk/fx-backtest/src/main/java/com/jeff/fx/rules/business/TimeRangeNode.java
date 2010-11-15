package com.jeff.fx.rules.business;

import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.gui.beanform.Label;
import com.jeff.fx.gui.beanform.Property;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.Operand;
import com.jeff.fx.rules.logic.AndNode;

public class TimeRangeNode extends AndNode<CandleFilterModel>
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
        from = new TimeOfWeek(TimeOfWeek.SUNDAY, 22, 00);
        fromInclusive = true;
        to = new TimeOfWeek(TimeOfWeek.FRIDAY, 21, 00);
        toInclusive = true;
        update();
    }

    public void update()
    {
        TimeOfWeekNode left = new TimeOfWeekNode(this);
        left.setTime(from);
        left.setOperand(fromInclusive ? Operand.ge : Operand.gt);
        super.left = left;
        
        TimeOfWeekNode right = new TimeOfWeekNode(this);
        right.setTime(to);
        right.setOperand(toInclusive ? Operand.le : Operand.lt);
        super.right = right;
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
