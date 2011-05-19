package com.jeff.fx.filter.candletype;

public class GravestoneDoji extends Doji
{
    public GravestoneDoji()
    {
        super();
        maxTailSize = 0.04;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bearish;
    }
}
