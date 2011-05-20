package com.jeff.fx.lfwd.filter;

import com.jeff.fx.lfwd.CandleFilterModel;

public class ExpressionFilter implements SimpleCandleFilter
{
    private String expression;

    public ExpressionFilter(String expression)
    {
        this.expression = expression;
    }

    @Override
    public boolean filter(CandleFilterModel model)
    {
        return model.evaluate(expression, Boolean.class);
    }
}
