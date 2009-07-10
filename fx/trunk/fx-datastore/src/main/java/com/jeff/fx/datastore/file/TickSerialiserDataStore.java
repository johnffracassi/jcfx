package com.jeff.fx.datastore.file;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;

@Component
public class TickSerialiserDataStore extends SerialiserDataStore<TickDataPoint>
{
	private Locator locator;
	
	public TickSerialiserDataStore()
	{
		locator = new Locator();
		locator.setExtension("ser");
	}
	
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period)
	{
		return false;
	}
}
