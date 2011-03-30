package com.jeff.fx.backtest.strategy.time;

import com.jeff.fx.backtest.engine.AbstractStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class StrategyInfo
{
    public static void main(String[] args)
    {
        StrategyInfo si = new StrategyInfo();
        si.process(TimeStrategy.class);
    }

    public void process(Class<? extends AbstractStrategy> strategyClass)
    {
        for (Field field : strategyClass.getDeclaredFields()) {
            System.out.println(field);

            for (Annotation annotation : field.getDeclaredAnnotations()) {
                System.out.println("  " + annotation);
            }
        }
    }
}
