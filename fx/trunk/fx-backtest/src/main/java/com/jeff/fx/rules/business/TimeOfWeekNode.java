package com.jeff.fx.rules.business;

import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.indicator.Label;
import com.jeff.fx.indicator.Property;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.Node;
import com.jeff.fx.rules.Operand;

public class TimeOfWeekNode extends AbstractFXNode
{
    @Property(key="operand")
    @Label("Operand")
    private Operand operand;
    
    @Property(key="time")
    @Label("Time Of Week")
    private TimeOfWeek time;
    
    public TimeOfWeekNode()
    {
        super(null);
        
        this.operand = Operand.eq;
        this.time = new TimeOfWeek(TimeOfWeek.SUNDAY, 22, 00);
    }
    
    public String getLabel() 
    {
        return "Time " + operand.getLabel() + " " + time;
    }

    public TimeOfWeekNode(Node<CandleFilterModel> parent)
    {
        super(parent);
    }

    @Override
    public boolean evaluate(CandleFilterModel model)
    {
        return operand.evaluate(new TimeOfWeek(model.getCandles().getCandle(model.getIndex()).getDateTime()), time);
    }

    public void setOperand(Operand operand)
    {
        this.operand = operand;
    }

    public Operand getOperand()
    {
        return operand;
    }

    public void setTime(TimeOfWeek time)
    {
        this.time = time;
    }

    public TimeOfWeek getTime()
    {
        return time;
    }
}
