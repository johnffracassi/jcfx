package com.jeff.fx.lfwd.filter;

import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.candlepattern.CandlePattern;

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
        return !pattern.is(model);
    }
}
