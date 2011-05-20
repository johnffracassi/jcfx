package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

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
