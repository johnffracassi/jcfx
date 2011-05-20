package com.jeff.fx.filter.candletype.def;

import com.jeff.fx.filter.MarketSentiment;

public class SpinningTop extends AbstractCandleTypeDef
{
    public SpinningTop()
    {
        minRange = 8;
        maxBodySize = 0.15;
        maxTailSize = 0.25;
        maxHeadSize = 0.25;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Indecision;
    }
}
