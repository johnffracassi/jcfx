package com.jeff.fx.datastore;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleStickDataPoint;
import com.jeff.fx.common.TickDataPoint;

@Component
public class FileDataStore
{
	@Autowired
	private String dataStorePath;
	
	public void init()
	{
		if(!checkExists())
		{
			createDataStore();
		}
	}

	public List<CandleStickDataPoint> loadCandles(LocalDate date)
	{
		return Collections.emptyList();
	}
	
	public void storeCandles(List<CandleStickDataPoint> candles)
	{
		
	}
	
	public List<TickDataPoint> loadTicks(LocalDate date)
	{
		return Collections.emptyList();
	}
	
	public void storeTicks(List<TickDataPoint> data)
	{
		
	}
	
	public File getDataStorePath()
	{
		File path = new File(dataStorePath);
		return path;
	}
	
	public boolean checkExists()
	{
		return getDataStorePath().exists();
	}
	
	public void createDataStore()
	{
		getDataStorePath().mkdirs();
	}
}
