package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

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
