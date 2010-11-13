package com.jeff.fx.rules.business;

import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.Node;
import com.jeff.fx.rules.Operand;
import com.jeff.fx.rules.logic.AndNode;


public class TimeOfWeekNodeFactory
{
    public static AbstractFXNode timeOfWeekIs(Node<CandleFilterModel> parent, final Operand operand, final TimeOfWeek timeOfWeek)
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
     
    public static Node<CandleFilterModel> timeOfWeekIsBetween(Node<CandleFilterModel> parent, final TimeOfWeek from, final TimeOfWeek to)
    {
        return new AndNode<CandleFilterModel>(parent, timeOfWeekIs(parent, Operand.ge, from), timeOfWeekIs(parent, Operand.lt, to));
    }

    public static Node<CandleFilterModel> timeOfWeekOutside(Node<CandleFilterModel> parent, final TimeOfWeek from, final TimeOfWeek to)
    {
        return new AndNode<CandleFilterModel>(parent, timeOfWeekIs(parent, Operand.lt, from), timeOfWeekIs(parent, Operand.gt, to));
    }
}
