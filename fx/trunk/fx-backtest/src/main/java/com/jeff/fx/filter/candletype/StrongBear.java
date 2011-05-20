package com.jeff.fx.filter.candletype;

public class StrongBear extends BasicCandleTypeDef
{
    public StrongBear()
    {
        maxSize = -6;
        minRange = 8;
        minBodySize = 0.5;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bearish;
    }
}
