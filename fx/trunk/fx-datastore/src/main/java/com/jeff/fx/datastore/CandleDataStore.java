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

import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

	public static void main(String[] args) throws IOException {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-cwl.xml");
		CandleDataStore cds = (CandleDataStore)ctx.getBean("candleDataStore");

		CandleDataResponse response = cds.loadCandles(new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 7, 20), Period.OneMin));
		CandleCollection cc = response.getCandles();
		CandleWeek cw = cc.getCandleWeek(new LocalDate(2010, 7, 20));
		System.out.println(cw.getCandle(0));
		System.out.println(cw.getCandle(cw.getCandleCount() - 1));
		
		response = cds.loadCandles(new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 7, 20), Period.OneHour));
		cc = response.getCandles();
		cw = cc.getCandleWeek(new LocalDate(2010, 7, 20));
		System.out.println(cw.getCandle(0));
		System.out.println(cw.getCandle(cw.getCandleCount() - 1));
	}
	
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
		File file = fileLocator.locate(request.getDataSource(), request.getInstrument(), request.getDate(), request.getPeriod());
		return file.exists();
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

	private void fillGaps(CandleWeek cw) {
		
//		int minutesInPeriod = (int)(cw.getPeriod().getInterval() / 1000 / 60);
//		int periodsInDay = 1440 / minutesInPeriod;
//		
//		// check off candles
//		CandleDataPoint[] candles = new CandleDataPoint[periodsInDay];
//		for(int c=0; c<candleList.size(); c++) {
//			CandleDataPoint candle = candleList.get(c);
//			
//			if(candle.getInstrument() == request.getInstrument()) {
//				int minuteOfDay = (candle.getDate().getMillisOfDay() / 1000 / 60);
//				int periodOfDay = minuteOfDay / minutesInPeriod;
//				candles[periodOfDay] = candle;
//			}
//		}
//		
//		// find and fill any missing candles
//		for(int c=0; c<periodsInDay; c++) {
//			if(candles[c] == null) {
//				if(c>0 && c<periodsInDay-1 && candles[c-1] != null && candles[c+1] != null) {
//					
//					// interpolate from surrounding candles
//					CandleDataPoint newCandle = new CandleDataPoint(candles[c-1]);
//					newCandle.setDateTime(newCandle.getDate().plusMinutes(minutesInPeriod));
//					newCandle.setBuyOpen(candles[c-1].getBuyClose());
//					newCandle.setSellOpen(candles[c-1].getSellClose());
//					newCandle.setBuyClose(candles[c+1].getBuyOpen());
//					newCandle.setSellClose(candles[c+1].getSellOpen());
//					newCandle.setBuyHigh(Math.max(newCandle.getBuyOpen(), newCandle.getBuyClose()));
//					newCandle.setSellHigh(Math.max(newCandle.getSellOpen(), newCandle.getSellClose()));
//					newCandle.setBuyLow(Math.min(newCandle.getBuyClose(), newCandle.getBuyClose()));
//					newCandle.setSellLow(Math.min(newCandle.getSellClose(), newCandle.getSellClose()));
//					newCandle.setTickCount(0);
//					newCandle.setBuyVolume(0);
//					newCandle.setSellVolume(0);
//					candles[c] = newCandle;
//					
//				} else if(c>0 && candles[c-1] != null) {
//					
//					// copy forward (values from previous candle)
//					CandleDataPoint newCandle = new CandleDataPoint(candles[c-1]);
//					newCandle.setDateTime(newCandle.getDate().plusMinutes(minutesInPeriod));
//					newCandle.setBuyOpen(candles[c-1].getBuyClose());
//					newCandle.setSellOpen(candles[c-1].getSellClose());
//					newCandle.setBuyClose(candles[c-1].getBuyClose());
//					newCandle.setSellClose(candles[c-1].getSellClose());
//					newCandle.setBuyHigh(candles[c-1].getBuyClose());
//					newCandle.setSellHigh(candles[c-1].getSellClose());
//					newCandle.setBuyLow(candles[c-1].getBuyClose());
//					newCandle.setSellLow(candles[c-1].getSellClose());
//					newCandle.setTickCount(0);
//					newCandle.setBuyVolume(0);
//					newCandle.setSellVolume(0);
//					candles[c] = newCandle;
//
//				} else {
//					System.out.println("*** missing candle at " + c + " ***");
//				}
//			}
//		}
	}
	
	private CandleWeek retrieve(FXDataRequest request) throws IOException {
		return retrieve(request.getDataSource(), request.getInstrument(), request.getDate(), request.getPeriod());
	}
		
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
	
	public Map<FXDataSource, DataSource<CandleDataPoint>> getCandleDataSources() {
		return candleDataSources;
	}

	public void setCandleDataSources(Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources) {
		this.candleDataSources = candleDataSources;
	}
}
