package com.jeff.fx.datasource;

import org.joda.time.LocalDate;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public interface Locator {

	public String generateUrl(Instrument instrument, LocalDate date, Period period);

}