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
import com.jeff.fx.indicator.advice.NaturalOrder;
import com.jeff.fx.indicator.line.ExponentialMovingAverage;
import com.jeff.fx.indicator.line.MACD;
import com.jeff.fx.indicator.line.SimpleMovingAverage;
import com.jeff.fx.indicator.line.WeightedMovingAverage;

@Component
public class BackTester {
	private static Logger log = Logger.getLogger(BackTester.class);

	@Autowired
	private DataManager dataManager;

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		BackTester backTester = (BackTester) ctx.getBean("backTester");
		backTester.run();
	}

	public void run() {
		try {
			FXDataResponse<CandleDataPoint> candles = dataManager.loadCandles(
					new FXDataRequest(FXDataSource.GAIN,Instrument.AUDUSD, 
							new Interval(new DateTime(2010, 4, 1, 0, 0, 0, 0), new DateTime(2010, 4, 17, 0, 0, 0, 0)), Period.FifteenMin));
			
			log.info("Found " + candles.getData().size() + " candles");
			
			NaturalOrder nos = new NaturalOrder(new SimpleMovingAverage[] { new SimpleMovingAverage(10), new SimpleMovingAverage(20), new SimpleMovingAverage(50) });
			NaturalOrder now = new NaturalOrder(new SimpleMovingAverage[] { new WeightedMovingAverage(10), new WeightedMovingAverage(20), new WeightedMovingAverage(50) });
			NaturalOrder noe = new NaturalOrder(new SimpleMovingAverage[] { new ExponentialMovingAverage(10), new ExponentialMovingAverage(20), new ExponentialMovingAverage(50) });
			MACD macd = new MACD(12, 26);
			
			for(CandleDataPoint candle : candles.getData()) {
				nos.add(candle);
				nos.add(candle);
				noe.add(candle);
				macd.add(candle);
				
				String smaOrder = nos.isNaturalOrderRising() ? "up" : nos.isNaturalOrderFalling() ? "down" : "none";
				String wmaOrder = now.isNaturalOrderRising() ? "up" : now.isNaturalOrderFalling() ? "down" : "none";
				String emaOrder = noe.isNaturalOrderRising() ? "up" : now.isNaturalOrderFalling() ? "down" : "none";

				System.out.printf("===| %s %.4f %s/%s/%s %n", candle, candle.getBuyOpen(), smaOrder, wmaOrder, emaOrder);
//				System.out.println(" " + nos);
//				System.out.println(" " + now);
//				System.out.println(" " + noe);
//				System.out.println(" " + candle + " / " + macd);
			}
			
		} catch (Exception ex) {
			log.error("Error loading candles", ex);
		}
	}
}
