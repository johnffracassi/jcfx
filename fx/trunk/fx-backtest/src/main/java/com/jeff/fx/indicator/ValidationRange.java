package com.jeff.fx.indicator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationRange {

	double min() default -Double.MAX_VALUE;
	double max() default Double.MAX_VALUE;
	
}
