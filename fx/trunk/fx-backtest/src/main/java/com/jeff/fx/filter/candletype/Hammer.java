package com.jeff.fx.filter.candletype;

public class Hammer extends HangingMan
{
    public Hammer()
    {
        minSize = 0.0;
        maxSize = Double.POSITIVE_INFINITY;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
