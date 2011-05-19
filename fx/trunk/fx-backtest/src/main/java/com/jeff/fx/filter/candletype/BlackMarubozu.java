package com.jeff.fx.filter.candletype;

public class BlackMarubozu extends BasicCandleTypeDef
{
    public BlackMarubozu()
    {
        minRange = 10;
        minBodySize = 0.94;
        maxHeadSize = 0.06;
        maxTailSize = 0.03;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Bearish;
    }
}
