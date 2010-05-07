package com.jeff.fx.common;

import java.util.TimeZone;


public enum FXDataSource {
	
	GAIN(TimeZone.getTimeZone("America/New_York")), 
	Forexite(TimeZone.getTimeZone("Europe/Zurich")), 
	Dukascopy(TimeZone.getTimeZone("Europe/Zurich"));
	
	public TimeZone timeZone;
	
	private FXDataSource(TimeZone tz) {
		timeZone = tz;
	}
}
