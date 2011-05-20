package com.jeff.fx.filter.candletype.def;

import com.jeff.fx.filter.MarketSentiment;

public class DragonFlyDoji extends AbstractCandleTypeDef
{
    public DragonFlyDoji()
    {
        minRange = 10;
        minSize = 0;
        maxBodySize = 0.05;
        maxHeadSize = 0.05;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bullish;
    }
}
