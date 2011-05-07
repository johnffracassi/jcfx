package com.jeff.fx.backtest.strategy.coder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface Optimiser
{
    double start();
    double end();
	double min();
    double max();
    double step() default 1;
}
