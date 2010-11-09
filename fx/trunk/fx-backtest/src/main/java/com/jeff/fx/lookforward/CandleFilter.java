package com.jeff.fx.lookforward;

import java.util.LinkedList;
import java.util.List;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.rules.Node;

public class CandleFilter
{
    public List<CandleDataPoint> apply(Node<CandleFilterModel> root, CandleCollection candles)
    {
        List<CandleDataPoint> filteredCandles = new LinkedList<CandleDataPoint>();
        IndicatorCache cache = new IndicatorCache();
        
        for (int index = 0, n = candles.getCandleCount(); index < n; index++)
        {
            CandleFilterModel model = new CandleFilterModel(candles, cache, index);
            if(root.evaluate(model))
            {
                filteredCandles.add(candles.getCandle(index));
            }
        }
        
        return filteredCandles;
    }
}
