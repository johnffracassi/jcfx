package com.jeff.fx.filter;

public class CandlePatternFilter implements SimpleCandleFilter
{
    private CandlePattern pattern;
    private int offset = 0;

    public CandlePatternFilter(CandlePattern pattern, int offset)
    {
        this.pattern = pattern;
        this.offset = offset;
    }

    @Override
    public boolean filter(CandleFilterModel model)
    {
        int idx = model.getIndex();
        if(idx - offset > 0)
            return !pattern.evaluate(model.getCandles().getCandle(idx - offset));
        else
            return false;
    }
}
