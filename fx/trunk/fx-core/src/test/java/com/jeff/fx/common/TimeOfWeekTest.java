package com.jeff.fx.common;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

public class TimeOfWeekTest {

	TimeOfWeek t1 = new TimeOfWeek(DateTimeConstants.SUNDAY, 00, 00);
	TimeOfWeek t2 = new TimeOfWeek(DateTimeConstants.MONDAY, 12, 00);
	TimeOfWeek t2a = new TimeOfWeek(DateTimeConstants.MONDAY, 00, 00);
	TimeOfWeek t2b = new TimeOfWeek(DateTimeConstants.MONDAY, 9, 00);
	TimeOfWeek t2c = new TimeOfWeek(DateTimeConstants.MONDAY, 15, 00);
	TimeOfWeek t3 = new TimeOfWeek(DateTimeConstants.WEDNESDAY, 12, 00);
	TimeOfWeek t4 = new TimeOfWeek(DateTimeConstants.FRIDAY, 12, 00);
	TimeOfWeek t5 = new TimeOfWeek(DateTimeConstants.SATURDAY, 12, 00);

    @Test
    public void shouldCreateATimeOfWeekFromAString()
    {
        String str = t3.toString();
        TimeOfWeek timeOfWeek = new TimeOfWeek(str);
        Assert.assertTrue(t3.equals(timeOfWeek));
    }

	@Test
	public void testPeriodOfWeek() {
		
		for(Period period : Period.values()) {
			Assert.assertTrue(period + "/" + t1 + " = " + t1.periodOfWeek(period), 0 == t1.periodOfWeek(period));
		}

		Assert.assertTrue(36 + " != " + t2.periodOfWeek(Period.OneHour), 36 == t2.periodOfWeek(Period.OneHour));
		
	}
	
	@Test
	public void testForWeek() {
		
		LocalDateTime dateTime = new LocalDateTime(2010, 9, 6, 1, 7, 0);
		
		TimeOfWeek tow = new TimeOfWeek(dateTime);

		LocalDateTime newDateTime = tow.forWeekContaining(dateTime);
		Assert.assertTrue(newDateTime.equals(dateTime));
		
		newDateTime = tow.forWeekContaining(dateTime.toLocalDate());
		Assert.assertTrue(newDateTime.equals(dateTime));
		
	}
	
	@Test
	public void testConstructors() {
		
		TimeOfWeek t1 = new TimeOfWeek(DateTimeConstants.SUNDAY, 15, 30);
		Assert.assertTrue(String.valueOf(t1), String.valueOf(t1).equalsIgnoreCase("Su1530"));
		
		TimeOfWeek t2 = new TimeOfWeek(DateTimeConstants.SATURDAY, 15, 30);
		Assert.assertTrue(String.valueOf(t2), String.valueOf(t2).equalsIgnoreCase("Sa1530"));
		
		TimeOfWeek t3 = new TimeOfWeek(DateTimeConstants.MONDAY, new LocalTime(10,45,00));
		Assert.assertTrue(String.valueOf(t3), String.valueOf(t3).equalsIgnoreCase("Mo1045"));
		
		TimeOfWeek t4 = new TimeOfWeek(DateTimeConstants.SUNDAY, new LocalTime(0,0,1));
		Assert.assertTrue(String.valueOf(t4), String.valueOf(t4).equalsIgnoreCase("Su0000"));
		
		TimeOfWeek t5 = new TimeOfWeek(0);
		Assert.assertTrue(String.valueOf(t5), String.valueOf(t5).equalsIgnoreCase("Su0000"));
		
		TimeOfWeek t6 = new TimeOfWeek(1439);
		Assert.assertTrue(String.valueOf(t6), String.valueOf(t6).equalsIgnoreCase("Su2359"));
		
		TimeOfWeek t7 = new TimeOfWeek(9365);
		Assert.assertTrue(String.valueOf(t7), String.valueOf(t7).equalsIgnoreCase("Sa1205"));
	}
	
	@Test
	public void testEquals() {
		Assert.assertTrue(new TimeOfWeek(0).equals(new TimeOfWeek(DateTimeConstants.SUNDAY, 0, 0)));
		Assert.assertTrue(new TimeOfWeek(1440).equals(new TimeOfWeek(DateTimeConstants.MONDAY, 0, 0)));
		Assert.assertTrue(new TimeOfWeek(10080).equals(new TimeOfWeek(DateTimeConstants.SUNDAY, 0, 0)));
		Assert.assertTrue(new TimeOfWeek(10079).equals(new TimeOfWeek(DateTimeConstants.SATURDAY, 23, 59)));

		Assert.assertFalse(new TimeOfWeek(0).equals(new TimeOfWeek(1)));
		Assert.assertFalse(new TimeOfWeek(1440).equals(new TimeOfWeek(2880)));
	}
	
	@Test
	public void testBefore() {
		
		Assert.assertTrue(t1.isBefore(t2));
		Assert.assertTrue(t2.isBefore(t3));
		Assert.assertTrue(t3.isBefore(t4));
		Assert.assertTrue(t4.isBefore(t5));
		Assert.assertTrue(t2a.isBefore(t2b));
		Assert.assertTrue(t2b.isBefore(t2c));
	
		Assert.assertFalse(t2.isBefore(t1));
		Assert.assertFalse(t5.isBefore(t1));
		Assert.assertFalse(t2b.isBefore(t2a));
		Assert.assertFalse(t2c.isBefore(t2b));
	}
	
	@Test
	public void testAfter() {
		
		Assert.assertTrue(t2.isAfter(t1));
		Assert.assertTrue(t3.isAfter(t2));
		Assert.assertTrue(t4.isAfter(t3));
		Assert.assertTrue(t5.isAfter(t4));
		Assert.assertTrue(t2b.isAfter(t2a));
		Assert.assertTrue(t2c.isAfter(t2b));
	
		Assert.assertFalse(t1.isAfter(t2));
		Assert.assertFalse(t1.isAfter(t5));
		Assert.assertFalse(t2a.isAfter(t2b));
		Assert.assertFalse(t2b.isAfter(t2c));
	}
	
}
