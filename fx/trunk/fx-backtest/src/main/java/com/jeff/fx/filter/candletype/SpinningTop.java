package com.jeff.fx.filter.candletype;

public class SpinningTop extends BasicCandleTypeDef
{
    public SpinningTop()
    {
        minRange = 8;
        maxBodySize = 0.15;
        maxTailSize = 0.25;
        maxHeadSize = 0.25;
    }

    @Override
    public CandleSentiment sentiment()
    {
        return CandleSentiment.Indecision;
    }
}
