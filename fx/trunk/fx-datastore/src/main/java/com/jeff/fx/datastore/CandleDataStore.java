package com.jeff.fx.datastore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleDataResponse;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datasource.DataSource;
import com.jeff.fx.datastore.file.FileLocator;
import com.jeff.fx.util.DateUtil;

@Component("candleDataStore")
public class CandleDataStore {

	private static Logger log = Logger.getLogger(CandleDataStore.class);

	@Autowired
	private ForexiteCandleWeekLoader loader;

	@Autowired
	private FileLocator fileLocator;
	
	private Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources;
	
	private List<DataStoreProgressListener> listeners = new ArrayList<DataStoreProgressListener>();

	public void addProgressListener(DataStoreProgressListener listener) {
		listeners.add(listener);
	}
	
	private void updateProgress(int progress, int steps) {
		if(listeners != null && listeners.size() > 0) {
			for(DataStoreProgressListener listener : listeners) {
				listener.dataStoreProgressUpdate(new DataStoreProgress(progress, steps));
			}
		}
	}
	
	/**
	 * does the requested data exist in the local data store?
	 * @param request
	 * @return
	 */
	public boolean exists(FXDataRequest request) {
		File file = fileLocator.locate(request.getDataSource(), request.getInstrument(), request.getDate(), request.getPeriod());
		return file.exists();
	}

	/**
	 * Load multiple weeks of data (from either local store or actual data source)
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public CandleDataResponse loadCandles(FXDataRequest request) throws IOException {
		
		if(request.isRangeOfDates()) {
			
			LocalDate sow1 = DateUtil.getStartOfWeek(request.getDate());
			LocalDate sow2 = DateUtil.getStartOfWeek(request.getEndDate());
			int weeks = Days.daysBetween(sow1, sow2).getDays() / 7 + 1;
			
			CandleCollection cc = new CandleCollection();
			for(int week=0; week<weeks; week++) {
				FXDataRequest newReq = new FXDataRequest(request);
				newReq.setDate(request.getDate().plusDays(week * 7));
				newReq.setEndDate(null);
				cc.putCandleWeek(loadCandlesForWeek(newReq));
				updateProgress(week, weeks);
			}
			return new CandleDataResponse(request, cc);			
		} else {			
			return new CandleDataResponse(request, new CandleCollection(loadCandlesForWeek(request)));
		}
	}
	
	/**
	 * Load candles from either local store or from the actual data source
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public CandleWeek loadCandlesForWeek(FXDataRequest request) throws IOException {
		
		log.debug("Load candles for " + request);

		// does the requested data already exist in the store?
		if (exists(request)) {
			log.debug("returning candles from data store");
			return retrieve(request.getDataSource(), request.getInstrument(), request.getDate(), request.getPeriod());
		} 

		// check if the M1 candles exists, if so then make the requested candles
		FXDataRequest newRequest = new FXDataRequest(request.getDataSource(), request.getInstrument(), request.getDate(), Period.OneMin);
		if(exists(newRequest)) {
			log.debug("requested period (" + request.getPeriod() + ") doesn't exist in store, converting from " + Period.OneMin + " candles");
			CandleWeek sourceCandles = retrieve(newRequest);
			CandleWeek targetCandles = new CandleWeek(sourceCandles, request.getPeriod());
			store(targetCandles);
			return targetCandles;
		}
		
		// can't find/convert data from store, go fetch from the source
		log.debug("fetching candles from data source");
		CandleWeek candleWeek = loader.load(request.getInstrument(), request.getDate(), request.getPeriod());
		store(candleWeek);
		return candleWeek;
	}

	
	/**
	 * Read from the local data store
	 * @param dataSource
	 * @param instrument
	 * @param date
	 * @param period
	 * @return
	 * @throws IOException
	 */
	private CandleWeek retrieve(FXDataRequest request) throws IOException {
		return retrieve(request.getDataSource(), request.getInstrument(), request.getDate(), request.getPeriod());
	}
		
	/**
	 * Read from the local data store
	 * @param dataSource
	 * @param instrument
	 * @param date
	 * @param period
	 * @return
	 * @throws IOException
	 */
	private CandleWeek retrieve(FXDataSource dataSource, Instrument instrument, LocalDate date, Period period) throws IOException {
			
		CandleWeek cw = null;

		if(date != null) {
			
			// locate the data file
			File file = fileLocator.locate(dataSource, instrument, date, period);
			log.debug("locating data store at " + file);
			
			if(file.exists()) {
				
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

				try {
					cw = (CandleWeek)ois.readObject();
					ois.close();
				} catch(ClassNotFoundException ex) {
					log.error("Error attempting to load from datastore", ex);
				} finally {
					ois.close();
				}
			}
		}
		
		return cw;
	}
	
	/**
	 * Write to the local data store
	 * @param data
	 * @throws IOException
	 */
	private void store(CandleWeek data) throws IOException {
		
		if(data != null) {
			
			// locate the data file
			File file = fileLocator.locate(data.getDataSource(), data.getInstrument(), data.getStartDate(), data.getPeriod());
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
			oos.writeObject(data);
			oos.close();
			
			log.info("successfully wrote candle week to store");
			
		} else {
			
			log.warn("null or empty list of data points supplied, not creating store");
		}
	}
	
	/**
	 * wipe the local data store
	 */
	public void clearStoreCache() throws IOException {
		File storeCacheRoot = new File(fileLocator.getDataRoot());
		FileUtils.deleteDirectory(storeCacheRoot);
	}
	
	public Map<FXDataSource, DataSource<CandleDataPoint>> getCandleDataSources() {
		return candleDataSources;
	}

	public void setCandleDataSources(Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources) {
		this.candleDataSources = candleDataSources;
	}
}
