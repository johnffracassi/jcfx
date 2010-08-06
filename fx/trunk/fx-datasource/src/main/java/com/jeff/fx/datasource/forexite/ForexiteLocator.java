package com.jeff.fx.datasource.forexite;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datasource.DownloadLocator;

@Component("forexiteLocator")
public class ForexiteLocator implements DownloadLocator {
	
	private static Logger log = Logger.getLogger(ForexiteLocator.class);

	// example URL - http://www.forexite.com/free_forex_quotes/2010/01/040110.zip
	
	private String domain = "www.forexite.com";
	private String pattern = "http://%s/free_forex_quotes/%04d/%02d/%02d%02d%02d.zip";

	public String generateUrl(Instrument instrument, LocalDate date, Period period) {
		
		int year = date.getYear();
		int month = date.getMonthOfYear();
		int day = date.getDayOfMonth();

		String url = String.format(pattern, domain, year, month, day, month, year % 100);

		log.debug(date + " => " + url);

		return url;
	}
}
