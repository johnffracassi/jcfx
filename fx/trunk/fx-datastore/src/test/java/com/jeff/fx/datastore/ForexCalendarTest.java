package com.jeff.fx.datastore;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.ForexCalendar;
import com.jeff.fx.common.TimeOfWeek;

public class ForexCalendarTest {
	
	private ForexCalendar cal = FXDataSource.Forexite.getCalendar();
	
	@Test
	public void testHolidays() {
		
		Assert.assertFalse("christmasEve", cal.isHoliday(new LocalDate(2009,12,24)));
		Assert.assertTrue("christmasDay", cal.isHoliday(new LocalDate(2009,12,25)));
		Assert.assertFalse("christmasBox", cal.isHoliday(new LocalDate(2009,12,26)));
		
		Assert.assertTrue("newYear2010/fr", cal.isHoliday(new LocalDate(2010,1,1)));
		Assert.assertFalse("newYear2010/sa", cal.isHoliday(new LocalDate(2010,1,2)));
		Assert.assertFalse("newYear2010/su", cal.isHoliday(new LocalDate(2010,1,3)));
		Assert.assertFalse("newYear2010/mo", cal.isHoliday(new LocalDate(2010,1,4)));
		
		Assert.assertTrue("newYear2009/th", cal.isHoliday(new LocalDate(2009,1,1)));
		Assert.assertFalse("newYear2009/fr", cal.isHoliday(new LocalDate(2009,1,2)));
		Assert.assertFalse("newYear2009/sa", cal.isHoliday(new LocalDate(2009,1,3)));
		Assert.assertFalse("newYear2009/su", cal.isHoliday(new LocalDate(2009,1,4)));

		Assert.assertTrue("newYear2011/sa", cal.isHoliday(new LocalDate(2011,1,1)));
		Assert.assertFalse("newYear2011/su", cal.isHoliday(new LocalDate(2011,1,2)));
		Assert.assertTrue("newYear2011/mo", cal.isHoliday(new LocalDate(2011,1,3)));
		Assert.assertFalse("newYear2011/tu", cal.isHoliday(new LocalDate(2011,1,4)));
	}
	
	@Test
	public void testStandardTimes() {
		
		TimeOfWeek beforeSundayOpen = new TimeOfWeek(0, 20, 0);
		Assert.assertFalse("beforeSundayOpen", cal.isOpen(beforeSundayOpen));
		
		TimeOfWeek atSundayOpen = new TimeOfWeek(0, 23, 0);
		Assert.assertTrue("atSundayOpen", cal.isOpen(atSundayOpen));
		
		TimeOfWeek afterSundayOpen = new TimeOfWeek(0, 23, 30);
		Assert.assertTrue("afterSundayOpen", cal.isOpen(afterSundayOpen));
		
		TimeOfWeek duringMonday = new TimeOfWeek(1, 15, 30);
		Assert.assertTrue("duringMonday", cal.isOpen(duringMonday));
		
		TimeOfWeek beforeFridayClose = new TimeOfWeek(5, 15, 30);
		Assert.assertTrue("beforeFridayClose", cal.isOpen(beforeFridayClose));
		
		TimeOfWeek atFridayClose = new TimeOfWeek(5, 22, 00);
		Assert.assertFalse("atFridayClose", cal.isOpen(atFridayClose));
		
		TimeOfWeek afterFridayClose = new TimeOfWeek(5, 23, 30);
		Assert.assertFalse("afterFridayClose", cal.isOpen(afterFridayClose));
	}
}
