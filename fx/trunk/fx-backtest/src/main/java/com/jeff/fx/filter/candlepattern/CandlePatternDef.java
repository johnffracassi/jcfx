package com.jeff.fx.filter.candlepattern;

import com.jeff.fx.filter.CandleFilterModel;
import com.jeff.fx.filter.MarketSentiment;

public interface CandlePatternDef
{
    boolean is(CandleFilterModel model);
    MarketSentiment sentiment();
}
