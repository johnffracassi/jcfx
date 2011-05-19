package com.jeff.fx.filter.candletype;

public class Doji extends BasicCandleTypeDef
{
    public Doji()
    {
        minRange = 8;
        maxBodySize = 0.05;
        maxTailSize = 0.65;
        maxHeadSize = 0.65;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Indecision;
    }
}
