package com.jeff.fx.common;

import org.joda.time.LocalDate;


public interface ForexCalendar {
	
	public boolean isHoliday(LocalDate date);
	public boolean isOpen(TimeOfWeek time);
	
}
