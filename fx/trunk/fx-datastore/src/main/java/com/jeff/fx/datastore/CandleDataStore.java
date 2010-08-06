package com.jeff.fx.datastore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleDataResponse;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.datasource.DataSource;
import com.jeff.fx.datasource.forexite.ForexiteLocator;
import com.jeff.fx.util.DateUtil;

@Component("candleDataStore")
public class CandleDataStore {

	private static Logger log = Logger.getLogger(CandleDataStore.class);

	private ForexiteLocator locator;
	private CandleWeekLoader loader;
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
	
	public boolean exists(FXDataRequest request) {
		return false;
	}

	public CandleDataResponse loadCandles(FXDataRequest request) throws IOException {
		
		if(request.isRangeOfDates()) {
			
			int weeks = Days.daysBetween(DateUtil.getStartOfWeek(request.getDate()), DateUtil.getStartOfWeek(request.getEndDate())).getDays() / 7;
			
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
	
	public CandleWeek loadCandlesForWeek(FXDataRequest request) throws IOException {
		
		log.debug("Load candles for " + request);

		// does the requested data already exist in the store?
//		if (exists(request)) {
//			log.debug("returning candles from data store");
//			return candleDataStore.load(request);
//		} 

		// check if the M1 candles exists, if so then make the requested candles
//		FXDataRequest newRequest = new FXDataRequest(request.getDataSource(), request.getInstrument(), request.getDate(), Period.OneMin);
//		if(exists(newRequest)) {
//			log.debug("requested period (" + request.getPeriod() + ") doesn't exist in store, converting from " + Period.OneMin + " candles");
//			List<CandleDataPoint> m1candles = candleDataStore.load(newRequest).getData();
//			CandleToCandleConverter c2c = new CandleToCandleConverter();
//			List<CandleDataPoint> convertedCandles = c2c.convert(m1candles, request.getPeriod());
//			candleDataStore.store(convertedCandles);
//			return new CandleDataResponse(request, convertedCandles);
//		}
		
		// can't find/convert data from store, go fetch from the source
		log.debug("fetching candles from data source");
		CandleWeek candleWeek = loader.load(request.getInstrument(), request.getDate(), request.getPeriod());
		store(candleWeek);
		return candleWeek;
	}

	private List<CandleDataPoint> filterAndFill(List<CandleDataPoint> candleList, FXDataRequest request) {
		
		int minutesInPeriod = (int)(request.getPeriod().getInterval() / 1000 / 60);
		int periodsInDay = 1440 / minutesInPeriod;
		
		// check off candles
		CandleDataPoint[] candles = new CandleDataPoint[periodsInDay];
		for(int c=0; c<candleList.size(); c++) {
			CandleDataPoint candle = candleList.get(c);
			
			if(candle.getInstrument() == request.getInstrument()) {
				int minuteOfDay = (candle.getDate().getMillisOfDay() / 1000 / 60);
				int periodOfDay = minuteOfDay / minutesInPeriod;
				candles[periodOfDay] = candle;
			}
		}
		
		// find and fill any missing candles
		for(int c=0; c<periodsInDay; c++) {
			if(candles[c] == null) {
				if(c>0 && c<periodsInDay-1 && candles[c-1] != null && candles[c+1] != null) {
					
					// interpolate from surrounding candles
					CandleDataPoint newCandle = new CandleDataPoint(candles[c-1]);
					newCandle.setDateTime(newCandle.getDate().plusMinutes(minutesInPeriod));
					newCandle.setBuyOpen(candles[c-1].getBuyClose());
					newCandle.setSellOpen(candles[c-1].getSellClose());
					newCandle.setBuyClose(candles[c+1].getBuyOpen());
					newCandle.setSellClose(candles[c+1].getSellOpen());
					newCandle.setBuyHigh(Math.max(newCandle.getBuyOpen(), newCandle.getBuyClose()));
					newCandle.setSellHigh(Math.max(newCandle.getSellOpen(), newCandle.getSellClose()));
					newCandle.setBuyLow(Math.min(newCandle.getBuyClose(), newCandle.getBuyClose()));
					newCandle.setSellLow(Math.min(newCandle.getSellClose(), newCandle.getSellClose()));
					newCandle.setTickCount(0);
					newCandle.setBuyVolume(0);
					newCandle.setSellVolume(0);
					candles[c] = newCandle;
					
				} else if(c>0 && candles[c-1] != null) {
					
					// copy forward (values from previous candle)
					CandleDataPoint newCandle = new CandleDataPoint(candles[c-1]);
					newCandle.setDateTime(newCandle.getDate().plusMinutes(minutesInPeriod));
					newCandle.setBuyOpen(candles[c-1].getBuyClose());
					newCandle.setSellOpen(candles[c-1].getSellClose());
					newCandle.setBuyClose(candles[c-1].getBuyClose());
					newCandle.setSellClose(candles[c-1].getSellClose());
					newCandle.setBuyHigh(candles[c-1].getBuyClose());
					newCandle.setSellHigh(candles[c-1].getSellClose());
					newCandle.setBuyLow(candles[c-1].getBuyClose());
					newCandle.setSellLow(candles[c-1].getSellClose());
					newCandle.setTickCount(0);
					newCandle.setBuyVolume(0);
					newCandle.setSellVolume(0);
					candles[c] = newCandle;

				} else {
					System.out.println("*** missing candle at " + c + " ***");
				}
			}
		}
		
		return Arrays.asList(candles);
	}
	
	/**
	 * 
	 */
	public void store(CandleWeek data) throws IOException
	{
		if(data != null) {
			
			// locate the data file
			File file = locator.locate(data.getDataSource(), data.getInstrument(), data.getStartDate(), data.getPeriod());
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
		}
		else
		{
			log.warn("null or empty list of data points supplied, not creating store");
		}
	}
	
	public Map<FXDataSource, DataSource<CandleDataPoint>> getCandleDataSources() {
		return candleDataSources;
	}

	public void setCandleDataSources(Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources) {
		this.candleDataSources = candleDataSources;
	}
}
