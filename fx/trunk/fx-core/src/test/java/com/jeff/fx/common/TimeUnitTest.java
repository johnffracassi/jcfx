package com.jeff.fx.common;

import org.junit.Assert;
import org.junit.Test;


public class TimeUnitTest {
 
	@Test
	public void testIntervals() {

		Assert.assertTrue("Second", TimeUnit.Second.getInterval() == 1000l);
		Assert.assertTrue("Minute", TimeUnit.Minute.getInterval() == 60000l);
		Assert.assertTrue("Hour", TimeUnit.Hour.getInterval() == 60000l * 60);
		Assert.assertTrue("Day", TimeUnit.Day.getInterval() == 86400000l);
		Assert.assertTrue("Week", TimeUnit.Week.getInterval() == 86400000l * 7);
		Assert.assertTrue("Month", TimeUnit.Month.getInterval() == 86400000l * 30);
		
	}
}
