package com.jeff.fx.filter.candletype;

public class Bear extends BasicCandleTypeDef
{
    public Bear()
    {
        maxSize = -4;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bearish;
    }
}
