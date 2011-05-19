package com.jeff.fx.filter.candletype;

public class HangingMan extends BasicCandleTypeDef
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
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bearish;
    }
}
