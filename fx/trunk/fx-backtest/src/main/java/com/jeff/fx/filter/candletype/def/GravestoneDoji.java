package com.jeff.fx.filter.candletype.def;

import com.jeff.fx.filter.MarketSentiment;

public class GravestoneDoji extends Doji
{
    public GravestoneDoji()
    {
        super();
        maxTailSize = 0.04;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bearish;
    }
}
