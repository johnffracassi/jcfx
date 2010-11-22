package com.jeff.fx.lookforward;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.rules.Node;

@Component
public class CandleFilterProcessor
{
    @Autowired
    private CandleFilterModelEvaluator evaluator;
    
    public List<CandleDataPoint> apply(Node root, CandleCollection candles)
    {
        if(candles == null)
            throw new RuntimeException("candles are null, not good");
        
        if(evaluator == null)
            throw new RuntimeException("ELEvaluator is null, not good");
        
        List<CandleDataPoint> filteredCandles = new LinkedList<CandleDataPoint>();
        IndicatorCache cache = new IndicatorCache();
        
        CandleFilterModel model = new CandleFilterModel(candles, cache, evaluator);
        for (int index = 0, n = candles.getCandleCount(); index < n; index++)
        {
            model.setIndex(index);
            
            if(root.evaluate(model))
            {
                filteredCandles.add(candles.getCandle(index));
            }
        }
        
        return filteredCandles;
    }
}
