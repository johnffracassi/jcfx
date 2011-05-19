package com.jeff.fx.filter.candletype;

public class FourPriceDoji extends Doji
{
    public FourPriceDoji()
    {
        this.maxRange = 0.04;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Indecision;
    }
}
