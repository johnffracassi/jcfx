package com.jeff.fx.datastore;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public class AbstractDataStore<T extends FXDataPoint> implements DataStore<T> 
{
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) 
	{
		return false;
	}

	public List<T> load(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) throws Exception 
	{
		return null;
	}

	public void store(List<T> data) throws Exception 
	{
		
	}
}
