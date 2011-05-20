package com.jeff.fx.lfwd;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;

public class CandleFilterModel
{
    private CandleCollection candles;
    private IndicatorCache cache;
    private CandleFilterModelEvaluator elEvaluator;
    private int index;

    public CandleFilterModel(CandleCollection candles, IndicatorCache cache, CandleFilterModelEvaluator evaluator)
    {
        super();
        this.candles = candles;
        this.cache = cache;
        this.index = 0;
        this.elEvaluator = evaluator;
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
    
    public void setIndex(int index)
    {
        this.index = index;
    }
    
    public Object evaluate(String expr)
    {
        return elEvaluator.evaluate(this, expr, null);
    }
    
    public <T> T evaluate(String expr, Class<T> returnType)
    {
        return elEvaluator.evaluate(this, expr, returnType);
    }
}
