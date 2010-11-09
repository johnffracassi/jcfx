package com.jeff.fx.lookforward;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;

public class CandleFilterModel
{
    private CandleCollection candles;
    private IndicatorCache cache;
    private int index;

    public CandleFilterModel(CandleCollection candles, IndicatorCache cache, int index)
    {
        super();
        this.candles = candles;
        this.cache = cache;
        this.index = index;
    }

    public CandleCollection getCandles()
    {
        return candles;
    }

    public IndicatorCache getCache()
    {
        return cache;
    }

    public int getIndex()
    {
        return index;
    }
}
