package com.jeff.fx.datastore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.DataSource;
import com.jeff.fx.datasource.converter.CandleToCandleConverter;

@Component("dataManager")
public class DataStoreImpl {

	private static Logger log = Logger.getLogger(DataStoreImpl.class);

	private DataStore<TickDataPoint> tickDataStore;
	private DataStore<CandleDataPoint> candleDataStore;
	private Map<FXDataSource, DataSource<TickDataPoint>> tickDataSources;
	private Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources;
	private List<DataStoreProgressListener> listeners = new ArrayList<DataStoreProgressListener>();

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		DataStoreImpl dm = (DataStoreImpl) ctx.getBean("dataManager");
		CandleToCandleConverter c2c = new CandleToCandleConverter();

		for(int i=1; i<30; i++) {
			LocalDate date = new LocalDate();
			date = date.minusDays(i);

			FXDataResponse<CandleDataPoint> response = dm.loadCandles(new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, date, Period.OneMin));
			
			log.debug("Loaded " + response.getData().size() + " ticks");

			for (Period period : new Period[] { Period.FiveMin, Period.TenMin, Period.FifteenMin, Period.ThirtyMin, Period.OneHour, Period.FourHour }) {
				dm.storeCandles(c2c.convert(response.getData(), period));
			}
		}
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
		log.debug("check existance of " + request);

		if (request.getPeriod() == Period.Tick) {
			return tickDataStore.exists(request);
		} else {
			return candleDataStore.exists(request);
		}
	}

	public FXDataResponse<CandleDataPoint> loadCandles(FXDataRequest request) throws Exception {
		
		if(request.isRangeOfDates()) {
			
			int dayCount = Days.daysBetween(request.getDate(), request.getEndDate()).getDays();
			
			List<CandleDataPoint> all = new LinkedList<CandleDataPoint>();
			for(int day = 0; day<dayCount; day++) {
				FXDataRequest newReq = new FXDataRequest(request);
				newReq.setDate(request.getDate().plusDays(day));
				newReq.setEndDate(null);
				all.addAll(loadCandlesForDay(newReq).getData());
				updateProgress(day, dayCount);
			}
			return new FXDataResponse<CandleDataPoint>(request, all);
		} else {
			return loadCandlesForDay(request);
		}
	}
	
	public FXDataResponse<CandleDataPoint> loadCandlesForDay(FXDataRequest request) throws Exception {
		
		log.debug("Load candles for " + request);

		// does the requested data already exist in the store?
		if (exists(request)) {
			log.debug("returning candles from data store");
			return candleDataStore.load(request);
		} 

		// check if the M1 candles exists, if so then make the requested candles
		FXDataRequest newRequest = new FXDataRequest(request.getDataSource(), request.getInstrument(), request.getDate(), Period.OneMin);
		if(exists(newRequest)) {
			log.debug("requested period (" + request.getPeriod() + ") doesn't exist in store, converting from " + Period.OneMin + " candles");
			List<CandleDataPoint> m1candles = candleDataStore.load(newRequest).getData();
			CandleToCandleConverter c2c = new CandleToCandleConverter();
			List<CandleDataPoint> convertedCandles = c2c.convert(m1candles, request.getPeriod());
			candleDataStore.store(convertedCandles);
			return new FXDataResponse<CandleDataPoint>(request, convertedCandles);
		}
		
		// TODO build candles from ticks if they exist
		
		// can't find/convert data from store, go fetch from the source
		log.debug("fetching candles from data source");
		DataSource<CandleDataPoint> ds = candleDataSources.get(request.getDataSource());
		FXDataResponse<CandleDataPoint> response = ds.load(request);
		List<CandleDataPoint> candles = response.getData();
		candles = filterAndFill(candles, request);
		storeCandles(candles);
		return response;
	}

	public FXDataResponse<TickDataPoint> loadTicks(FXDataRequest request) throws Exception {
		log.debug("Load ticks for " + request);

		if (exists(request)) {
			log.debug("returning ticks from data store");
			return tickDataStore.load(request);
		} else {
			log.debug("fetching ticks from data source");
			DataSource<TickDataPoint> ds = tickDataSources.get(request.getDataSource());
			FXDataResponse<TickDataPoint> response = ds.load(request);
			storeTicks(response.getData());
			return response;
		}
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
	
	public void storeTicks(List<TickDataPoint> data) throws Exception {
		log.info("storing " + data.size() + " ticks in data store");
		tickDataStore.store(data);
	}

	public void storeCandles(List<CandleDataPoint> data) throws Exception {
		log.info("storing " + data.size() + " candles in data store");
		candleDataStore.store(data);
	}

	public DataStore<TickDataPoint> getTickDataStore() {
		return tickDataStore;
	}

	public void setTickDataStore(DataStore<TickDataPoint> tickDataStore) {
		this.tickDataStore = tickDataStore;
	}

	public DataStore<CandleDataPoint> getCandleDataStore() {
		return candleDataStore;
	}

	public void setCandleDataStore(DataStore<CandleDataPoint> candleDataStore) {
		this.candleDataStore = candleDataStore;
	}

	public Map<FXDataSource, DataSource<TickDataPoint>> getTickDataSources() {
		return tickDataSources;
	}

	public void setTickDataSources(
			Map<FXDataSource, DataSource<TickDataPoint>> tickDataSources) {
		this.tickDataSources = tickDataSources;
	}

	public Map<FXDataSource, DataSource<CandleDataPoint>> getCandleDataSources() {
		return candleDataSources;
	}

	public void setCandleDataSources(
			Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources) {
		this.candleDataSources = candleDataSources;
	}
}
