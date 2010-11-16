package com.jeff.fx.lookforward;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;

public class CandleFilterModelEvaluator
{
    public static <T> T evaluate(CandleFilterModel model, String expression, Class<T> returnType)
    {
        // Create or retrieve a JexlEngine
        JexlEngine jexl = new JexlEngine();
        
        // Create an expression object
        Expression e = jexl.createExpression( expression );

        CandleDataPointList list = new CandleDataPointList(model.getCandles(), model.getIndex());
        CandleDataPoint candle = list.get(0);
        
        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("candles", list);
        jc.set("candle", candle);
        jc.set("date", candle.getDateTime().toLocalDate());
        jc.set("time", candle.getDateTime().toLocalTime());
        jc.set("price", candle.evaluate(CandleValueModel.Typical));
        
        // Now evaluate the expression, getting the result
        return (T)e.evaluate(jc);
    }
}

