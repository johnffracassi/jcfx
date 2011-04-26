package com.jeff.fx.filter;

import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.jeff.fx.indicator.Indicator;

@Component
public class IndicatorMap extends HashMap<String,Class<? extends Indicator>>
{
    public void setIndicators(Set<Indicator> allIndicators)
    {
        for(Indicator indicator : allIndicators)
        {
            put(indicator.getKey(), indicator.getClass());
        }
    }
    
    @Override
    public Class<? extends Indicator> get(Object key)
    {
        return super.get(key);
    }
}
