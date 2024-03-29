package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class ShootingStar extends AbstractCandleTypeDef
{
    public ShootingStar()
    {
        minRange = 10;
        minSize = 0.0;
        maxSize = Double.POSITIVE_INFINITY;

        minHeadSize = 0.70;
        maxTailSize = 0.05;
        maxBodySize = 0.20;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bearish;
    }
}
