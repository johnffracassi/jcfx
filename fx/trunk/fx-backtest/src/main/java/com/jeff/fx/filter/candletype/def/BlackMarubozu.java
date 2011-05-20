package com.jeff.fx.filter.candletype.def;

import com.jeff.fx.filter.MarketSentiment;

public class BlackMarubozu extends AbstractCandleTypeDef
{
    public BlackMarubozu()
    {
        minRange = 10;
        maxSize = -10;
        minBodySize = 0.94;
        maxHeadSize = 0.06;
        maxTailSize = 0.06;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bearish;
    }
}
