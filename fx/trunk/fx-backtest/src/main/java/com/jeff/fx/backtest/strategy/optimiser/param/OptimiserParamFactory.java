package com.jeff.fx.backtest.strategy.optimiser.param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OptimiserParamFactory
{
    private Map<Class<?>,Class<? extends OptimiserParameter>> map;

    @Autowired
    public OptimiserParamFactory(List<OptimiserParameter> params)
    {
        map = new HashMap<Class<?>,Class<? extends OptimiserParameter>>();

        for (OptimiserParameter param : params) {
            register(param);
        }
    }

    private void register(OptimiserParameter param)
    {
        for (Class aClass : param.registerFor())
        {
            map.put(aClass, param.getClass());
        }
    }

    public Class<? extends OptimiserParameter> getParamClassFor(Class<?> type)
    {
        if(map.containsKey(type))
        {
            return map.get(type);
        }

        throw new RuntimeException(String.format("Could not map type '%s' to an OptimiserParameter class", type.getSimpleName()));
    }
}
