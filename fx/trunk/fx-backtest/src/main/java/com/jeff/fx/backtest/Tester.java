package com.jeff.fx.backtest;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datastore.DataManager;
import com.jeff.fx.datastore.converter.TickToCandleConverter;

public class Tester {

	private static Logger log = Logger.getLogger(Tester.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"context-datastore.xml");
		DataManager dm = (DataManager) ctx.getBean("dataManager");

		FXDataResponse<TickDataPoint> response = dm
				.loadTicks(new FXDataRequest(FXDataSource.GAIN,
						Instrument.AUDUSD, new Interval(new DateTime(2009, 6,
								8, 0, 0, 0, 0), new DateTime(2009, 6, 8, 0, 0,
								0, 0)), Period.Tick));
		log.debug("Loaded " + response.getData().size() + " ticks");

		TickToCandleConverter t2c = new TickToCandleConverter();

		for (Period period : new Period[] { Period.OneMin, Period.FiveMin,
				Period.FifteenMin, Period.ThirtyMin, Period.OneHour }) {
			List<CandleDataPoint> candles = t2c.convert(response.getData(),
					period);
			dm.getCandleDataStore().store(candles);
		}
	}
}
