package com.jeff.fx.common;

public enum FXDataSource {
	
	GAIN(new ForexiteCalendar()), 
	Forexite(new ForexiteCalendar()), 
	Dukascopy(new ForexiteCalendar());
	
	public ForexCalendar cal;
	
	private FXDataSource(ForexiteCalendar cal) {
		this.cal = cal;
	}
	
	public ForexCalendar getCalendar() {
		return cal;
	}
}
