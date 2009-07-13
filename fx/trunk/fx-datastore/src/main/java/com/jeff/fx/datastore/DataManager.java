package com.jeff.fx.datastore;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.DataSource;

public class DataManager
{
	private static Logger log = Logger.getLogger(DataManager.class);
	
	private DataStore<TickDataPoint> tickDataStore;
	private DataStore<CandleDataPoint> candleDataStore;
	private Map<FXDataSource,DataSource<TickDataPoint>> tickDataSources;
	private Map<FXDataSource,DataSource<CandleDataPoint>> candleDataSources;
	
	public static void main(String[] args) throws Exception 
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		DataManager dm = (DataManager)ctx.getBean("dataManager");
				
		List<TickDataPoint> ticks = dm.loadTicks(FXDataSource.GAIN, Instrument.EURUSD, new LocalDateTime(2009, 6, 8, 11, 0, 0));
		
		log.debug("Loaded " + ticks.size() + " ticks");
	}
	
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) 
	{
		log.debug("check existance of [" + dataSource + "/" + instrument + "/" + period + "/" + dateTime + "]");
		
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
		log.debug("Load candles for [" + dataSource + "/" + instrument + "/" + period + "/" + dateTime + "]");
		DataSource<CandleDataPoint> ds = candleDataSources.get(dataSource);
		return ds.load(instrument, dateTime, period);
	}
	
	public List<TickDataPoint> loadTicks(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime) throws Exception
	{
		log.debug("Load ticks for [" + dataSource + "/" + instrument + "/" + dateTime + "]");

		if(exists(dataSource, instrument, dateTime, Period.Tick))
		{
			log.debug("returning ticks from data store");
			return tickDataStore.load(dataSource, instrument, dateTime, Period.Tick);
		}
		else
		{
			log.debug("fetching ticks from data source");
			DataSource<TickDataPoint> ds = tickDataSources.get(dataSource);
			List<TickDataPoint> ticks = ds.load(instrument, dateTime, Period.Tick);
			storeTicks(ticks);
			return ticks;
		}
	}
	
	public void storeTicks(List<TickDataPoint> data) throws Exception 
	{
		log.info("storing " + data.size() + " ticks in data store");
		tickDataStore.store(data);
	}

	public void storeCandles(List<CandleDataPoint> data) throws Exception 
	{
		log.info("storing " + data.size() + " candles in data store");
		candleDataStore.store(data);
	}

	public DataStore<TickDataPoint> getTickDataStore() 
	{
		return tickDataStore;
	}

	public void setTickDataStore(DataStore<TickDataPoint> tickDataStore) 
	{
		this.tickDataStore = tickDataStore;
	}

	public DataStore<CandleDataPoint> getCandleDataStore() 
	{
		return candleDataStore;
	}

	public void setCandleDataStore(DataStore<CandleDataPoint> candleDataStore) 
	{
		this.candleDataStore = candleDataStore;
	}

	public Map<FXDataSource, DataSource<TickDataPoint>> getTickDataSources() 
	{
		return tickDataSources;
	}

	public void setTickDataSources(
			Map<FXDataSource, DataSource<TickDataPoint>> tickDataSources) 
	{
		this.tickDataSources = tickDataSources;
	}

	public Map<FXDataSource, DataSource<CandleDataPoint>> getCandleDataSources() 
	{
		return candleDataSources;
	}

	public void setCandleDataSources(
			Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources) 
	{
		this.candleDataSources = candleDataSources;
	}
}
