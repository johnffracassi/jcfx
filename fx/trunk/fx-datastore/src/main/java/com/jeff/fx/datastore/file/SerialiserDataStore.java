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
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.datastore.DataStore;

@Component
public class SerialiserDataStore<T extends FXDataPoint> implements DataStore<T>
{
	private static Logger log = Logger.getLogger(SerialiserDataStore.class);
	
	private Locator locator;
	
	public boolean exists(FXDataRequest request)
	{
		File[] files = locator.locate(request);
		
		boolean exists = true;
		for(File file : files)
		{
			if(!file.exists())
			{
				exists = false;
				break;
			}
		}

		log.debug("all files " + (exists?"":"do not") + " exist in data store");
		
		return exists;
	}

	@SuppressWarnings("unchecked")
	public FXDataResponse<T> load(FXDataRequest request) throws Exception
	{
		if(exists(request))
		{
			// locate the data file
			File[] files = locator.locate(request);

			List<T> allData = new ArrayList<T>();
			for(File file : files)
			{
				// deserialise the list of data points
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				List<T> list = (List<T>)ois.readObject();
				ois.close();
				
				allData.addAll(list);
			}
			
			return new FXDataResponse<T>(request, allData);
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
			LocalDate date = sample.getDate().toLocalDate();
			
			// locate the data file
			File file = locator.locate(dataSource, instrument, date, sample.getPeriod());
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

	public Locator getLocator() 
	{
		return locator;
	}

	public void setLocator(Locator locator) 
	{
		this.locator = locator;
	}
}
