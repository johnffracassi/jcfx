package com.jeff.fx.lookforward;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class CandleFilterModelEvaluator
{
    public static <T> T evaluate(CandleFilterModel model, String expression, Class<T> returnType)
    {
        // Create or retrieve a JexlEngine
        JexlEngine jexl = new JexlEngine();
        
        // Create an expression object
        Expression e = jexl.createExpression( expression );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("candles", model.getCandles());
        jc.set("candle", model.getCandles().getCandle(model.getIndex()));
        jc.set("idx", model.getIndex());

        // Now evaluate the expression, getting the result
        return (T)e.evaluate(jc);
    }
}
