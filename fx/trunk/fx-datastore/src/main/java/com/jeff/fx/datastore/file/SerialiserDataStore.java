package com.jeff.fx.datastore.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.AbstractDataStore;

@Component
public class SerialiserDataStore<T extends FXDataPoint> extends AbstractDataStore<T>
{
	private static Logger log = Logger.getLogger(SerialiserDataStore.class);

	@SuppressWarnings("unchecked")
	public FXDataResponse<T> load(FXDataRequest request) throws Exception
	{
		if(exists(request)) {
			
			// locate the data file
			File file = getLocator().locate(request);

			// deserialise the list of data points
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			List<T> list = (List<T>)ois.readObject();
			ois.close();
				
			return new FXDataResponse<T>(request, list);
		} else {
			log.warn("file does not exist in store, returning empty list of data points");
			return new FXDataResponse<T>(request, Collections.<T>emptyList());
		}
	}

	/**
	 * 
	 */
	public void store(List<T> data) throws Exception
	{
		if(data != null && data.size() > 0) {
			
			DataPointOrganiser organiser = new DataPointOrganiser();
			Map<DPKey, List<T>> map = organiser.organise(data);
			
			// break into separate files
			for(DPKey key : map.keySet()) {
				
				// locate the data file
				File file = getLocator().locate(key.dataSource, key.instrument, key.date, key.period);
				log.debug("locating data store at " + file);
				
				// if the file exists, delete and replace (check for existence should be performed beforehand)
				if(file.exists()) {
					log.info("store exists already, over-writing file " + file);
					file.delete();
				}
				
				// check that the parent directory exists, if not, create it
				if(!file.getParentFile().exists()) {
					log.info("creating directory for store - " + file.getParentFile());
					file.getParentFile().mkdirs();
				}
				
				// serialise the list to the file
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(map.get(key));
				oos.close();
				
				log.info("successfully wrote " + data.size() + " data points to store");
			}
		}
		else
		{
			log.warn("null or empty list of data points supplied, not creating store");
		}
	}

	
	// organise data points into separate stores. Should be keyed by datasource/instrument/date
	private class DataPointOrganiser {
		
		public Map<DPKey, List<T>> map;
	
		public DataPointOrganiser() {
			map = new HashMap<DPKey, List<T>>();
		}
		
		public Map<DPKey, List<T>> organise(List<T> data) {
			for(T dp : data) {
				addDataPoint(dp);
			}
			
			return map;
		}
		
		private void addDataPoint(T dataPoint) {
			DPKey key = new DPKey(dataPoint);
			
			if(map.containsKey(key)) {
				map.get(key).add(dataPoint);
			} else {
				List<T> list = new LinkedList<T>();
				list.add(dataPoint);
				map.put(key, list);
			}
		}
	}

	private class DPKey {
		
		public FXDataSource dataSource;
		public LocalDate date;
		public Instrument instrument;
		public Period period;
		
		public DPKey(T dp) {
			dataSource = dp.getDataSource();
			date = dp.getDate().toLocalDate();
			instrument = dp.getInstrument();
			period = dp.getPeriod();
		}
		
		@Override
		public int hashCode() {
			return (dataSource + "#" + date + "#" + instrument + "#" + period).hashCode();
		}
		
		@Override
		public boolean equals(Object otherObj) {
			DPKey key = (DPKey)otherObj;
			return dataSource.equals(key.dataSource) && date.equals(key.date) && instrument.equals(key.instrument) && period.equals(key.period);
		}
	}
}



