package com.jeff.fx.indicator;

import java.io.IOException;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.strategy.time.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataResponse;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;

@Component("indicatorTest")
public class IndicatorTest {

	@Autowired
	private CandleDataStore dataManager;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		IndicatorTest app = (IndicatorTest)ctx.getBean("indicatorTest");
		app.run();
	}
	
	public void run() {
		try {
			FXDataRequest request = new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 8, 1), new LocalDate(2010, 8, 12), Period.OneMin);
			CandleDataResponse cdr = dataManager.loadCandles(request);
			CandleCollection cc = cdr.getCandles();
			ZigZagIndicator zzi = new ZigZagIndicator();
			List<IndicatorMarker> idxs = zzi.calculate(cc);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void smaTest() {
		FXDataRequest request = new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 8, 1), new LocalDate(2010, 8, 12), Period.OneMin);
		
		try {
			CandleDataResponse cdr = dataManager.loadCandles(request);
			CandleCollection cc = cdr.getCandles();
			
			SimpleMovingAverage sma7 = (SimpleMovingAverage)IndicatorCache.calculate(new SimpleMovingAverage(7, CandleValueModel.Typical), cc);
			SimpleMovingAverage sma28 = (SimpleMovingAverage)IndicatorCache.calculate(new SimpleMovingAverage(28, CandleValueModel.Typical), cc);
			
			for(int i=0; i<cc.getCandleCount(); i++) {
				System.out.println(i + ") " + cc.getCandle(i) + " - sma(7)=" + sma7.getValue(i) + "/" + sma7.getDirection(i) + " sma(28)=" + sma28.getValue(i) + "/" + sma7.getDirection(i));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

