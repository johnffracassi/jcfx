package com.jeff.fx.indicator;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

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

		FXDataRequest request = new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 8, 1), new LocalDate(2010, 8, 12), Period.OneMin);
		
		try {
			CandleDataResponse cdr = app.dataManager.loadCandles(request);
			CandleCollection cc = cdr.getCandles();
			SimpleMovingAverage sma = new SimpleMovingAverage(14, CandleValueModel.Typical);
			sma.calculate(cc);
			
			for(int i=0; i<sma.getLength(); i++) {
				System.out.println(i + ") " + cc.getCandle(i) + " - " + sma.getValue(i));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

