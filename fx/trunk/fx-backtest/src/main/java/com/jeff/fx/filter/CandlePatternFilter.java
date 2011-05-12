package com.jeff.fx.filter;

public class CandlePatternFilter implements SimpleCandleFilter
{
    CandlePattern pattern;

    public CandlePatternFilter(CandlePattern pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public boolean filter(CandleFilterModel model, int idx)
    {
        return !pattern.evaluate(model.getCandles().getCandle(idx));
    }
}
