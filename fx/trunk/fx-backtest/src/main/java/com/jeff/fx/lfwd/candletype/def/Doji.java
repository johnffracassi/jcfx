package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class Doji extends AbstractCandleTypeDef
{
    public Doji()
    {
        minRange = 10;
        maxSize = 2;
        minSize = -2;
        minHeadSize = 0.10;
        minTailSize = 0.10;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Indecision;
    }
}
