package com.jeff.fx.datastore;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public interface DataStore<T extends FXDataPoint> 
{
	/**
	 * 
	 * 
	 * @param data
	 * @return success indicator
	 */
	public void store(List<T> data) throws Exception;
	
	public List<T> load(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) throws Exception;
	
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period);
}
