package com.jeff.fx.rules.business;

import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.rules.Node;
import com.jeff.fx.rules.Operand;
import com.jeff.fx.rules.logic.AndNode;


public class TimeOfWeekNodeFactory
{
    public static AbstractFXNode timeOfWeekIs(Node parent, final Operand operand, final TimeOfWeek timeOfWeek)
    {
        AbstractFXNode node = new AbstractFXNode(parent) {
            public boolean evaluate(CandleFilterModel model) {
                return operand.evaluate(new TimeOfWeek(model.getCandles().getCandle(model.getIndex()).getDateTime()), timeOfWeek);
            }
            
            public String getLabel() {
                return "Time " + operand.getLabel() + " " + timeOfWeek;
            }
        };
        
        return node;
    }
     
    public static Node timeOfWeekIsBetween(Node parent, final TimeOfWeek from, final TimeOfWeek to)
    {
        return new AndNode(parent, timeOfWeekIs(parent, Operand.ge, from), timeOfWeekIs(parent, Operand.lt, to));
    }

    public static Node timeOfWeekOutside(Node parent, final TimeOfWeek from, final TimeOfWeek to)
    {
        return new AndNode(parent, timeOfWeekIs(parent, Operand.lt, from), timeOfWeekIs(parent, Operand.gt, to));
    }
}
