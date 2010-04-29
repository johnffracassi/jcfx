package com.jeff.fx.common;

import java.util.TimeZone;


public enum FXDataSource {
	
	GAIN(TimeZone.getTimeZone("GMT")), 
	Forexite(TimeZone.getTimeZone("GMT"));
	
	public TimeZone timeZone;
	
	private FXDataSource(TimeZone tz) {
		timeZone = tz;
	}
}
