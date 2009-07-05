package com.jeff.fx.datastore.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datastore.DataStore;

@Component
public class TickSerialiserDataStore implements DataStore<TickDataPoint>
{
	private Locator locator;
	
	public TickSerialiserDataStore()
	{
		locator = new Locator();
		locator.setExtension("ser");
	}
	
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period)
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<TickDataPoint> load(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) throws Exception
	{
		if(exists(dataSource, instrument, dateTime, period))
		{
			// locate the data file
			File file = locator.locate(dataSource, instrument, dateTime, period);

			// deserialise the list of candles
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			List<TickDataPoint> list = (List<TickDataPoint>)ois.readObject();
			ois.close();
			
			return list;
		}
		else
		{
			return Collections.<TickDataPoint>emptyList();
		}
	}

	/**
	 * 
	 */
	public void store(List<TickDataPoint> data) throws Exception
	{
		if(data != null && data.size() > 0)
		{
			TickDataPoint sample = data.get(0);
			FXDataSource dataSource = sample.getDataSource();
			Instrument instrument = sample.getInstrument();
			LocalDateTime dateTime = sample.getDate();
			
			// locate the data file
			File file = locator.locate(dataSource, instrument, dateTime, Period.Tick);
			
			// if the file exists, delete and replace (check for existence should be performed beforehand)
			if(file.exists())
			{
				file.delete();
			}
			
			// check that the parent directory exists, if not, create it
			if(!file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			
			// serialise the list to the file
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(data);
			oos.close();
		}
	}
}
