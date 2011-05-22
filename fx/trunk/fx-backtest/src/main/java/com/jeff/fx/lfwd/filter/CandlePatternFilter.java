package com.jeff.fx.lfwd.filter;

import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.candlepattern.CandlePattern;

public class CandlePatternFilter implements SimpleCandleFilter
{
    private CandlePattern pattern;

    public CandlePatternFilter(CandlePattern pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public boolean filter(CandleFilterModel model)
    {
        return !pattern.is(model);
    }
}
