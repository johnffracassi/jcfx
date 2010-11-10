package com.jeff.fx.rules.business;

import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.Node;
import com.jeff.fx.rules.Operand;
import com.jeff.fx.rules.logic.AndNode;


public class TimeOfWeekNodeFactory
{
    public static AbstractFXNode timeOfWeekIs(final Operand operand, final TimeOfWeek timeOfWeek)
    {
        AbstractFXNode node = new AbstractFXNode() {
            public boolean evaluate(CandleFilterModel model) {
                return operand.evaluate(new TimeOfWeek(model.getCandles().getCandle(model.getIndex()).getDateTime()), timeOfWeek);
            }
        };
        
        return node;
    }
     
    public static Node<CandleFilterModel> timeOfWeekIsBetween(final TimeOfWeek from, final TimeOfWeek to)
    {
        return new AndNode<CandleFilterModel>(timeOfWeekIs(Operand.ge, from), timeOfWeekIs(Operand.lt, to));
    }

    public static Node<CandleFilterModel> timeOfWeekOutside(final TimeOfWeek from, final TimeOfWeek to)
    {
        return new AndNode<CandleFilterModel>(timeOfWeekIs(Operand.lt, from), timeOfWeekIs(Operand.gt, to));
    }
}
