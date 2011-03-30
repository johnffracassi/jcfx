package com.jeff.fx.backtest.strategy.coder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface Optimiser
{
	double min() default 0;
    double max() default 250;
    double step() default 1;
}
