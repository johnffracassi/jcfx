package com.jeff.fx.filter.candletype;

public class DragonFlyDoji extends Doji
{
    public DragonFlyDoji()
    {
        super();
        maxHeadSize = 0.04;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bullish;
    }
}
