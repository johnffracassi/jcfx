package com.jeff.fx.datasource.forexite;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Test;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public class ForexiteLocatorTest {

	@Test
	public void generateUrl() {
		
		ForexiteLocator locator = new ForexiteLocator();
		
		String expected = "http://www.forexite.com/free_forex_quotes/2010/01/040110.zip";
		String actual = locator.generateUrl(Instrument.AUDUSD, new DateTime(2010, DateTimeConstants.JANUARY, 4, 0, 0, 0, 0).toLocalDate(), Period.OneMin);
		
		assertEquals(expected, actual);
	}
}
