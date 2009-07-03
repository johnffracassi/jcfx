package com.jeff.fx.datastore.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataStore;

@Component
public class CandleSerialiserDataStore implements DataStore<CandleDataPoint>
{
	private Locator locator;

	public static void main(String[] args)
	{
		CandleDataPoint p1 = new CandleDataPoint();
		p1.setDataSource(FXDataSource.GAIN);
		p1.setInstrument(Instrument.AUDUSD);
		p1.setDate(new LocalDateTime());
		p1.setPeriod(Period.FiveMin);
		
		CandleDataPoint p2 = new CandleDataPoint(p1);
		
		List<CandleDataPoint> list = new ArrayList<CandleDataPoint>();
		list.add(p1);
		list.add(p2);
		
		CandleSerialiserDataStore ds = new CandleSerialiserDataStore();
		
		try
		{
			ds.store(list);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			List<CandleDataPoint> candles = ds.load(FXDataSource.GAIN, Instrument.AUDUSD, new LocalDateTime(), Period.FiveMin);
			System.out.println(candles);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public CandleSerialiserDataStore()
	{
		locator = new Locator();
		locator.setExtension("ser");
	}
	
	public boolean exists(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period)
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<CandleDataPoint> load(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period) throws Exception
	{
		if(exists(dataSource, instrument, dateTime, period))
		{
			// locate the data file
			File file = locator.locate(dataSource, instrument, dateTime, period);

			// deserialise the list of candles
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			List<CandleDataPoint> list = (List<CandleDataPoint>)ois.readObject();
			ois.close();
			
			return list;
		}
		else
		{
			return Collections.<CandleDataPoint>emptyList();
		}
	}

	/**
	 * 
	 */
	public void store(List<CandleDataPoint> data) throws Exception
	{
		if(data != null && data.size() > 0)
		{
			CandleDataPoint sample = data.get(0);
			FXDataSource dataSource = sample.getDataSource();
			Instrument instrument = sample.getInstrument();
			LocalDateTime dateTime = sample.getDate();
			Period period = sample.getPeriod();
			
			// locate the data file
			File file = locator.locate(dataSource, instrument, dateTime, period);
			
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
