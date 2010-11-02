package com.jeff.fx.backtest.indicator;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.indicator.CommodityChannelIndex;

@Component("indicatorValidator")
public class IndicatorValidator {

	@Autowired
	private CandleDataStore loader;

	private FXDataSource dataSource = FXDataSource.Forexite;
	private Instrument instrument = Instrument.GBPUSD;
	private Period period = Period.OneMin;
	private LocalDate startDate = new LocalDate(2010, 10, 20);

	public static void main(String[] args) throws Exception {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		IndicatorValidator sv = (IndicatorValidator) ctx.getBean("indicatorValidator");
		sv.run();
	}
	
	public void run() {

		FXDataRequest request = new FXDataRequest();
		request.setDataSource(dataSource);
		request.setDate(startDate);
		request.setEndDate(startDate);
		request.setInstrument(instrument);
		request.setPeriod(period);
		
		try 
		{
			CandleWeek cw = loader.loadCandlesForWeek(request);

			Indicator ind = new CommodityChannelIndex(14);
			ind.calculate(new CandleCollection(cw));
			
			for(int i=0, n=cw.getCandleCount(); i<n; i++) 
			{
			    CandleDataPoint candle = cw.getCandle(i);
			    float value = ind.getValue(i);
			    
				System.out.printf("%d,%s,%s,%.4f,%.4f,%.4f,%.4f,%.5f,%.5f\n", i, candle.getDateTime().toLocalDate(), candle.getDateTime().toLocalTime(), cw.getRawValues(0)[i], cw.getRawValues(1)[i], cw.getRawValues(2)[i], cw.getRawValues(3)[i], value, candle.evaluate(CandleValueModel.Typical));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
