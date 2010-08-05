package com.jeff.fx.common;

import java.util.TimeZone;

import org.joda.time.LocalDate;


public interface ForexCalendar {
	
	public boolean isHoliday(LocalDate date);
	public boolean isOpen(TimeOfWeek time);
	
	public TimeOfWeek getOpenTime();
	public TimeOfWeek getCloseTime();
	public TimeZone getTimeZone();
	
}
