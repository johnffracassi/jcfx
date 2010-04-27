package com.jeff.fx.datastore.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.datastore.AbstractDataStore;

@Component
public class SerialiserDataStore<T extends FXDataPoint> extends AbstractDataStore<T>
{
	private static Logger log = Logger.getLogger(SerialiserDataStore.class);

	public FXDataResponse<T> load(FXDataRequest request) throws Exception
	{
		List<T> all = new ArrayList<T>();
		
		int dayCount = Days.daysBetween(request.getInterval().getStart(), request.getInterval().getEnd()).getDays() + 1;

		log.debug(dayCount + " days in the interval (" + request.getInterval() + ")");
		
		for(int i=0; i<dayCount; i++) {
			List<T> list = loadForDay(request, i).getData();
			log.debug("found " + list.size() + " candles for " + request.getInterval().getStart().plusDays(i));
			all.addAll(list);
		}
		
		FXDataResponse<T> response = new FXDataResponse<T>(request, all);
		return response;
	}

	@SuppressWarnings("unchecked")
	private FXDataResponse<T> loadForDay(FXDataRequest request, int day) throws Exception
	{
		if(exists(request, day))
		{
			// locate the data file
			File file = getLocator().locate(request, day);

			// deserialise the list of data points
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			List<T> list = (List<T>)ois.readObject();
			ois.close();
				
			return new FXDataResponse<T>(request, list);
		}
		else
		{
			log.warn("file does not exist in store, returning empty list of data points");
			return new FXDataResponse<T>(request, Collections.<T>emptyList());
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
			DateTime date = sample.getDate().toDateTime();
			
			// locate the data file
			File file = getLocator().locate(dataSource, instrument, date, sample.getPeriod());
			log.debug("locating data store at " + file);
			
			// if the file exists, delete and replace (check for existence should be performed beforehand)
			if(file.exists())
			{
				log.info("store exists already, over-writing file " + file);
				file.delete();
			}
			
			// check that the parent directory exists, if not, create it
			if(!file.getParentFile().exists())
			{
				log.info("creating directory for store - " + file.getParentFile());
				file.getParentFile().mkdirs();
			}
			
			// serialise the list to the file
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(data);
			oos.close();
			
			log.info("successfully wrote " + data.size() + " data points to store");
		}
		else
		{
			log.warn("null or empty list of data points supplied, not creating store");
		}
	}
}
