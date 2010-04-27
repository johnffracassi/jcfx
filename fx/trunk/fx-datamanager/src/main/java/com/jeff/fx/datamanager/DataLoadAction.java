package com.jeff.fx.datamanager;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.converter.TickToCandleConverter;
import com.jeff.fx.datastore.DataManager;


public class DataLoadAction {
	
	private static Logger log = Logger.getLogger(DataLoadAction.class);

	public void perform(FXDataSource dataSource, Instrument instrument, DateTime date) {
		try {
			DataManager dm = (DataManager) DataManagerApp.ctx.getBean("dataManager");
			FXDataResponse<TickDataPoint> response = dm.loadTicks(new FXDataRequest(dataSource, instrument, new Interval(date, date), Period.Tick));
			
			log.debug("Loaded " + response.getData().size() + " ticks");
			
			TickToCandleConverter t2c = new TickToCandleConverter();

			for (Period period : new Period[] { Period.OneMin, Period.FiveMin, Period.FifteenMin, Period.ThirtyMin, Period.OneHour }) {
				List<CandleDataPoint> candles = t2c.convert(response.getData(),period);
				dm.getCandleDataStore().store(candles);
			}
		} catch(Exception ex) {
			log.error(ex);
		}
	}
}
