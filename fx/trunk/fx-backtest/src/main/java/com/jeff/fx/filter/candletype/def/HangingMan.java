package com.jeff.fx.filter.candletype.def;

import com.jeff.fx.filter.MarketSentiment;

public class HangingMan extends AbstractCandleTypeDef
{
    public HangingMan()
    {
        minRange = 10;
        minSize = Double.NEGATIVE_INFINITY;
        maxSize = 0.0;

        minTailSize = 0.70;
        maxHeadSize = 0.05;
        maxBodySize = 0.20;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bearish;
    }
}
