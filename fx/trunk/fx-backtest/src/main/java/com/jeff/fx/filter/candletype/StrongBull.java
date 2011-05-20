package com.jeff.fx.filter.candletype;

public class StrongBull extends BasicCandleTypeDef
{
    public StrongBull()
    {
        minSize = 6;
        minRange = 8;
        minBodySize = 0.5;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
