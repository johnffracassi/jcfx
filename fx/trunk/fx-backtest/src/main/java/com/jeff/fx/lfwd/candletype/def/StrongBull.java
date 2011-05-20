package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class StrongBull extends AbstractCandleTypeDef
{
    public StrongBull()
    {
        minSize = 6;
        minRange = 8;
        minBodySize = 0.5;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bullish;
    }
}
