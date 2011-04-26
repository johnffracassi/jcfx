package com.jeff.fx.filter;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.indicator.AbstractIndicator;

/**
 * visit any indicator value
 */
@Component
public class IndicatorEvaluator extends HashMap<String,IndicatorResultList>
{
    @Autowired
    private IndicatorCache cache;
    
    private CandleFilterModel model;
    
    public IndicatorEvaluator()
    {
    }
    
    public void setModel(CandleFilterModel model)
    {
        this.model = model;
    }
    
    public IndicatorResultList get(Object keyObj) 
    {
        String key = String.valueOf(keyObj);
        
//        IndicatorResultCache irCache = new IndicatorResultCache();
        
        String paramsStr = key.substring( key.indexOf('(') + 1, key.length() - 1);
        String[] params = paramsStr.split(",");
        String keyStr = key.substring(0, key.indexOf('('));
        
        try
        {
            AbstractIndicator indicator = (AbstractIndicator)cache.getIndicator(keyStr, params);
            
            if(indicator.requiresCalculation())
            {
                indicator.calculate(model.getCandles());
            }
            
            return new IndicatorResultList(indicator.getValues(), model.getIndex());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}