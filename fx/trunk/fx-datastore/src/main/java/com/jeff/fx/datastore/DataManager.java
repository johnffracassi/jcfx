package com.jeff.fx.datastore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.DataSource;
import com.jeff.fx.datasource.gain.GAINDataSource;
import com.jeff.fx.datastore.file.CandleSerialiserDataStore;
import com.jeff.fx.datastore.file.TickSerialiserDataStore;

public class DataManager
{
	private DataStore<TickDataPoint> tickDataStore;
	private DataStore<CandleDataPoint> candleDataStore;
	private Map<FXDataSource,DataSource<TickDataPoint>> tickDataSources;
	private Map<FXDataSource,DataSource<CandleDataPoint>> candleDataSources;
	
	public void init()
	{
		tickDataStore = new TickSerialiserDataStore();
		candleDataStore = new CandleSerialiserDataStore();
		
		tickDataSources = new HashMap<FXDataSource, DataSource<TickDataPoint>>();
		tickDataSources.put(FXDataSource.GAIN, new GAINDataSource());

		candleDataSources = new HashMap<FXDataSource, DataSource<CandleDataPoint>>();
	}
	
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) 
	{
		if(period == Period.Tick)
		{
			return tickDataStore.exists(dataSource, instrument, dateTime, Period.Tick);
		}
		else
		{
			return candleDataStore.exists(dataSource, instrument, dateTime, period);
		}
	}

	public List<CandleDataPoint> loadCandles(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) throws Exception
	{
		DataSource<CandleDataPoint> ds = candleDataSources.get(dataSource);
		return ds.load(instrument, dateTime, period);
	}
	
	public List<TickDataPoint> loadTicks(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime) throws Exception
	{
		DataSource<TickDataPoint> ds = tickDataSources.get(dataSource);
		return ds.load(instrument, dateTime, Period.Tick);
	}
	
	public void storeTicks(List<TickDataPoint> data) throws Exception 
	{
		tickDataStore.store(data);
	}

	public void storeCandles(List<CandleDataPoint> data) throws Exception 
	{
		candleDataStore.store(data);
	}
}
