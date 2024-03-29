package com.jeff.fx.lfwd.candletype.def;

import com.jeff.fx.lfwd.MarketSentiment;

public class InvertedHammer extends AbstractCandleTypeDef
{
    public InvertedHammer()
    {
        minRange = 10;
        minSize = Double.NEGATIVE_INFINITY;
        maxSize = 0.0;

        minHeadSize = 0.70;
        maxTailSize = 0.05;
        maxBodySize = 0.20;
    }

    @Override
    public MarketSentiment sentiment()
    {
        return MarketSentiment.Bullish;
    }
}
