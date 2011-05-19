package com.jeff.fx.filter.candletype;

public class ShootingStar extends BasicCandleTypeDef
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
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bearish;
    }
}
