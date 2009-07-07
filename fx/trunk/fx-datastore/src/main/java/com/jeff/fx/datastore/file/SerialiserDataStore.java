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

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataStore;

@Component
public class SerialiserDataStore<T extends FXDataPoint> implements DataStore<T>
{
	private Locator locator;
	
	public SerialiserDataStore()
	{
		locator = new Locator();
		locator.setExtension("ser");
	}
	
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period)
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<T> load(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) throws Exception
	{
		if(exists(dataSource, instrument, dateTime, period))
		{
			// locate the data file
			File file = locator.locate(dataSource, instrument, dateTime, period);

			// deserialise the list of candles
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			List<T> list = (List<T>)ois.readObject();
			ois.close();
			
			return list;
		}
		else
		{
			return Collections.<T>emptyList();
		}
	}

	/**
	 * 
	 */
	public void store(List<T> data) throws Exception
	{
		if(data != null && data.size() > 0)
		{
			T sample = data.get(0);
			FXDataSource dataSource = sample.getDataSource();
			Instrument instrument = sample.getInstrument();
			LocalDateTime dateTime = sample.getDate();
			
			// locate the data file
			File file = locator.locate(dataSource, instrument, dateTime, sample.getPeriod());
			
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
