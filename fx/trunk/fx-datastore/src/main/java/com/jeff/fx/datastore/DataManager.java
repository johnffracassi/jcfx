package com.jeff.fx.datastore;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
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
import com.jeff.fx.datastore.converter.TickToCandleConverter;

@Component("dataManager")
public class DataManager {
	private static Logger log = Logger.getLogger(DataManager.class);

	private DataStore<TickDataPoint> tickDataStore;
	private DataStore<CandleDataPoint> candleDataStore;
	private Map<FXDataSource, DataSource<TickDataPoint>> tickDataSources;
	private Map<FXDataSource, DataSource<CandleDataPoint>> candleDataSources;

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		DataManager dm = (DataManager) ctx.getBean("dataManager");

		FXDataResponse<TickDataPoint> response = dm.loadTicks(new FXDataRequest(FXDataSource.GAIN, Instrument.AUDUSD, new Interval(new DateTime(2009, 6, 8, 0, 0, 0, 0), new DateTime(2009, 6, 8, 0, 0, 0, 0)), Period.Tick));
		log.debug("Loaded " + response.getData().size() + " ticks");

		TickToCandleConverter t2c = new TickToCandleConverter();

		for (Period period : new Period[] { Period.OneMin, Period.FiveMin,
				Period.FifteenMin, Period.ThirtyMin, Period.OneHour }) {
			List<CandleDataPoint> candles = t2c.convert(response.getData(),
					period);
			dm.getCandleDataStore().store(candles);
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

	public FXDataResponse<CandleDataPoint> loadCandles(FXDataRequest request)
			throws Exception {
		log.debug("Load candles for " + request);
		DataSource<CandleDataPoint> ds = candleDataSources.get(request
				.getDataSource());
		return ds.load(request);
	}

	public FXDataResponse<TickDataPoint> loadTicks(FXDataRequest request)
			throws Exception {
		log.debug("Load ticks for " + request);

		if (exists(request)) {
			log.debug("returning ticks from data store");
			return tickDataStore.load(request);
		} else {
			log.debug("fetching ticks from data source");
			DataSource<TickDataPoint> ds = tickDataSources.get(request
					.getDataSource());
			FXDataResponse<TickDataPoint> response = ds.load(request);
			storeTicks(response.getData());
			return response;
		}
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
