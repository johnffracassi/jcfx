package com.jeff.fx.filter.candletype;

public class StrongBear extends BasicCandleTypeDef
{
    public StrongBear()
    {
        maxSize = -5;
        minRange = 8;
        minBodySize = 0.4;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bearish;
    }
}
