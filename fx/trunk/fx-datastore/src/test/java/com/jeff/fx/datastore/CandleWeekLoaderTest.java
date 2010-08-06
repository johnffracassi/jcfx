package com.jeff.fx.datastore;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.jeff.fx.util.DateUtil;


public class CandleWeekLoaderTest {

	@Test
	public void testStartOfWeek() {
		Assert.assertTrue(DateUtil.getStartOfWeek(new LocalDate(2010, 8, 1)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(DateUtil.getStartOfWeek(new LocalDate(2010, 8, 3))), DateUtil.getStartOfWeek(new LocalDate(2010, 8, 3)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(DateUtil.getStartOfWeek(new LocalDate(2010, 8, 5))), DateUtil.getStartOfWeek(new LocalDate(2010, 8, 5)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(DateUtil.getStartOfWeek(new LocalDate(2010, 8, 7))), DateUtil.getStartOfWeek(new LocalDate(2010, 8, 7)).equals(new LocalDate(2010, 8, 1)));
		Assert.assertTrue(String.valueOf(DateUtil.getStartOfWeek(new LocalDate(2010, 8, 9))), DateUtil.getStartOfWeek(new LocalDate(2010, 8, 9)).equals(new LocalDate(2010, 8, 8)));
		Assert.assertTrue(String.valueOf(DateUtil.getStartOfWeek(new LocalDate(2010, 8, 11))), DateUtil.getStartOfWeek(new LocalDate(2010, 8, 11)).equals(new LocalDate(2010, 8, 8)));
	}
}
