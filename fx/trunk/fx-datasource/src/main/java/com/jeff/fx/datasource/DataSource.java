package com.jeff.fx.datasource;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public interface DataSource<T extends FXDataPoint> 
{
	public List<T> load(Instrument instrument, LocalDateTime dateTime, Period period) throws Exception;
}
