package com.jeff.fx.datastore;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;

public interface DataStore<T extends FXDataPoint> 
{
	public void store(List<T> data);
	public List<T> load(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime);
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime);
}
