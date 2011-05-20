package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

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
