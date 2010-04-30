package com.jeff.fx.datasource.gain;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datasource.Locator;
import com.jeff.fx.util.DateUtil;

@Component("GAINLocator")
public class GAINLocator implements Locator {

	private static Logger log = Logger.getLogger(GAINLocator.class);
	private String domain = "ratedata.gaincapital.com";
	private String pattern = "http://%s/%4d/%02d %s/%s_%s_Week%d.zip";
	private static final int MID_WEEK_DOW = 3;
	

	public String generateUrl(Instrument instrument, LocalDate date, Period period) {
		
		int year = date.getYear();
		int month = calculateMonth(date);
		String cur1 = instrument.toString().substring(0, 3);
		String cur2 = instrument.toString().substring(3);

		String url = String.format(pattern, domain, year, month + 1, DateUtil.MONTHS[month], cur1, cur2, calculateWeek(date));

		log.debug(date + " => " + url);

		return url;
	}

	private int calculateMonth(LocalDate date) {
		
		return roundDate(date).getMonthOfYear() - 1;
	}

	private int calculateWeek(LocalDate date) {
		
		LocalDate rounded = roundDate(date);
		return (rounded.getDayOfMonth() - 1) / 7 + 1;
	}

	private LocalDate roundDate(LocalDate date) {
		
		int dayOfWeek = date.getDayOfWeek() % 7;

		if (dayOfWeek < MID_WEEK_DOW) {
			return date.plusDays(MID_WEEK_DOW - dayOfWeek);
		} else if (dayOfWeek > MID_WEEK_DOW) {
			return date.minusDays(dayOfWeek - MID_WEEK_DOW);
		} else {
			return date;
		}
	}
}
