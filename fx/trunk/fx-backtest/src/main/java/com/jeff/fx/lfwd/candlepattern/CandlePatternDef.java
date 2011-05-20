package com.jeff.fx.lfwd.candlepattern;

import com.jeff.fx.lfwd.CandleFilterModel;
import com.jeff.fx.lfwd.MarketSentiment;

public interface CandlePatternDef
{
    boolean is(CandleFilterModel model);
    MarketSentiment sentiment();
}
