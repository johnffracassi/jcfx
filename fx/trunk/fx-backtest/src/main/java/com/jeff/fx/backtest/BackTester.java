package com.jeff.fx.backtest;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
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
import com.jeff.fx.datastore.DataStoreImpl;

@Component
public class BackTester {
	
	private static Logger log = Logger.getLogger(BackTester.class);

	@Autowired
	private DataStoreImpl dataManager;

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		BackTester backTester = (BackTester) ctx.getBean("backTester");
		backTester.run();
	}

	public void run() {
		try {
			FXDataRequest request = new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 4, 1), new LocalDate(2010, 4, 30), Period.FifteenMin);
			FXDataResponse<CandleDataPoint> candles = dataManager.loadCandles(request);
			
//			HighLowFinder hlf = new HighLowFinder();
//			for(CandleDataPoint candle : candles.getData()) {
//				hlf.add(candle);
//			}
			
			
		} catch (Exception ex) {
			log.error("Error loading candles", ex);
		}
	}
}
