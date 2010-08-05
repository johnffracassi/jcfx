package com.jeff.fx.datastore;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;


public class CandleWeekLoaderTest {

	@Test
	public void testStartOfWeek() {
		
		Assert.assertTrue(CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 1)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 3))), CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 3)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 5))), CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 5)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 7))), CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 7)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 9))), CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 9)).equals(new LocalDate(2010, 8, 8)));
		Assert.assertTrue(String.valueOf(CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 11))), CandleWeekLoader.getStartOfWeek(new LocalDate(2010, 8, 11)).equals(new LocalDate(2010, 8, 8)));
	}
}
