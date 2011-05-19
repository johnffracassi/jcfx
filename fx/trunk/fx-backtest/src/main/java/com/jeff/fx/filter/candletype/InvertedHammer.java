package com.jeff.fx.filter.candletype;

public class InvertedHammer extends BasicCandleTypeDef
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
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
