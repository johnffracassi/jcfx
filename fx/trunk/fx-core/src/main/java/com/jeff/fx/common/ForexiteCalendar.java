package com.jeff.fx.common;

import java.util.TimeZone;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;


public class ForexiteCalendar implements ForexCalendar {

	private TimeZone zone = TimeZone.getTimeZone("Europe/Zurich");
	private TimeOfWeek open = new TimeOfWeek(DateTimeConstants.SUNDAY, 23, 00);
	private TimeOfWeek close = new TimeOfWeek(DateTimeConstants.FRIDAY, 22, 00);
	
	public ForexiteCalendar(TimeZone zone, TimeOfWeek open, TimeOfWeek close) {
		super();
		this.zone = zone;
		this.open = open;
		this.close = close;
	}

	public ForexiteCalendar() {
	}
	
	public boolean isOpen(TimeOfWeek time) {
		return (time.isAfter(open) && time.isBefore(close));
	}
	
	public TimeOfWeek getOpenTime() {
		return open;
	}
	
	public TimeOfWeek getCloseTime() {
		return close;
	}
	
	public boolean isHoliday(LocalDate date) {
		
		// Christmas
		if(date.getDayOfMonth() == 25 && date.getMonthOfYear() == 12) {
			return true;
		}
		
		// new year
		if(date.getMonthOfYear() == 1 && date.getDayOfYear() <= 3) {
			if(date.getDayOfYear() == 1) {
				return true;
			} else if(date.getDayOfYear() <= 3 && date.getDayOfWeek() == DateTimeConstants.MONDAY) {
				return true;
			}
		} 
		
		return false;
	}
	
	public TimeZone getZone() {
		return zone;
	}

	public void setZone(TimeZone zone) {
		this.zone = zone;
	}

	public TimeOfWeek getOpen() {
		return open;
	}

	public void setOpen(TimeOfWeek open) {
		this.open = open;
	}

	public TimeOfWeek getClose() {
		return close;
	}

	public void setClose(TimeOfWeek close) {
		this.close = close;
	}
}
