package com.jeff.fx.datamanager;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;


public class DataLoadAction {
	
	private static Logger log = Logger.getLogger(DataLoadAction.class);

	public void perform(FXDataSource dataSource, Instrument instrument, LocalDate date) {
		try {
//			DataStoreImpl dm = (DataStoreImpl) DataManagerApp.ctx.getBean("dataManager");
//			FXDataResponse<TickDataPoint> response = dm.loadTicks(new FXDataRequest(dataSource, instrument, date, Period.Tick));
//			
//			log.debug("Loaded " + response.getData().size() + " ticks");
//			
//			TickToCandleConverter t2c = new TickToCandleConverter();
//
//			for (Period period : new Period[] { Period.OneMin, Period.FiveMin, Period.FifteenMin, Period.ThirtyMin, Period.OneHour }) {
//				List<CandleDataPoint> candles = t2c.convert(response.getData(),period);
//				dm.getCandleDataStore().store(candles);
//			}
		} catch(Exception ex) {
			log.error(ex);
		}
	}
}
