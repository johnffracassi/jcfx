package com.jeff.fx.lookforward;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;

@Component("evaluator")
public class CandleFilterModelEvaluator
{
    @Autowired
    private IndicatorEvaluator indicatorEvaluator;
    
    public CandleFilterModelEvaluator()
    {
    }

    public Object evaluate(CandleFilterModel model, String expression)
    {
        // Create or retrieve a JexlEngine
        JexlEngine jexl = new JexlEngine();
        
        // Create an expression object
        Expression e = jexl.createExpression( expression );

        CandleDataPointList list = new CandleDataPointList(model.getCandles(), model.getIndex());
        CandleDataPoint candle = list.get(0);
        
        indicatorEvaluator.setModel(model);
        
        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("candles", list);
        jc.set("candle", candle);
        jc.set("date", candle.getDateTime().toLocalDate());
        jc.set("time", candle.getDateTime().toLocalTime());
        jc.set("price", candle.evaluate(CandleValueModel.Typical));
        jc.set("idx", model.getIndex());
        jc.set("ind", indicatorEvaluator);
        
        Object result = e.evaluate(jc);
        
        System.out.println(expression + " = " + result);
        
        // Now evaluate the expression, getting the result
        return result;
    }
    
    public <T> T evaluate(CandleFilterModel model, String expression, Class<T> returnType)
    {
        return (T)evaluate(model, expression);
    }
}

