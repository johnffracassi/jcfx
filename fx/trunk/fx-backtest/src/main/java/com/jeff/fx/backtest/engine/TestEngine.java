package com.jeff.fx.backtest.engine;

import java.util.List;

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
import com.jeff.fx.datastore.DataManager;

@Component
public class TestEngine {

	@Autowired
	private DataManager dataManager;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		TestEngine app = (TestEngine)ctx.getBean("testEngine");
		app.run();
	}

	public void run() {
		
		BTParameterSet pbs = new BTParameterSet();
		
		BTParameterValueSet pvs1 = new BTParameterValueSet();
		pvs1.setStartValue(10);
		pvs1.setEndValue(100);
		pvs1.setStep(5);
		
		BTParameterValueSet pvs2 = new BTParameterValueSet();
		pvs2.setStartValue(10);
		pvs2.setEndValue(50);
		pvs2.setStep(5);
		
		pbs.setParameter("openAfter", pvs1);
		pbs.setParameter("closeAfter", pvs2);
		
		try {
			
			FXDataRequest request = new FXDataRequest(FXDataSource.Forexite, Instrument.AUDUSD, new LocalDate(2010, 4, 1), new LocalDate(2010, 4, 30), Period.OneMin);
			FXDataResponse<CandleDataPoint> candles = dataManager.loadCandles(request);
			execute(candles.getData(), SimpleStrategy.createTestSet(pbs));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public BTResult execute(List<CandleDataPoint> candles, List<SimpleStrategy> tests) {
		
		System.out.println("running " + tests.size() + " iterations with " + candles.size() + " candles");
		
		for(SimpleStrategy strategy : tests) {
			for(CandleDataPoint candle : candles) {
				strategy.candle(candle);
			}
			
			System.out.println(strategy);
		}
		
		return new BTResult();
	}
}