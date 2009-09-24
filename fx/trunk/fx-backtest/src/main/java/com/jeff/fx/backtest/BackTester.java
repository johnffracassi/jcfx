package com.jeff.fx.backtest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataManager;

@Component
public class BackTester {
	private static Logger log = Logger.getLogger(BackTester.class);

	@Autowired
	private DataManager dataManager;

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"context-*.xml");
		BackTester backTester = (BackTester) ctx.getBean("backTester");
		backTester.run();
	}

	public void run() {
		try {
			FXDataResponse<CandleDataPoint> candles = dataManager
					.loadCandles(new FXDataRequest(FXDataSource.GAIN,
							Instrument.AUDUSD, new Interval(new DateTime(2009,
									6, 8, 0, 0, 0, 0), new DateTime(2009, 6, 8,
									0, 0, 0, 0)), Period.FifteenMin));
			log.info("Found " + candles.getData().size() + " candles");
		} catch (Exception ex) {
			log.error("Error loading candles", ex);
		}
	}
}
