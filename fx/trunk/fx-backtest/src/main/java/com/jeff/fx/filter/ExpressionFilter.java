package com.jeff.fx.filter;

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
        System.out.printf("%.4f - %.4f = %d %n", model.evaluate("candle.open"), model.evaluate("candle.close"), model.evaluate("candle.size"));
        return model.evaluate(expression, Boolean.class);
    }
}
