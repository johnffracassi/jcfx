package com.jeff.fx.backtest.indicator;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.indicator.RelativeStrengthIndicator;

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
    
    public void run() throws IOException {

        CandleCollection candles = loadTestData();
        Indicator indicator = new RelativeStrengthIndicator(14);
        indicator.calculate(candles);
        outputIndicatorData(candles, indicator);
    }
    
    public void outputIndicatorData(CandleCollection candles, Indicator indicator)
    {
        float[] open = candles.getRawValues(0);
        float[] high = candles.getRawValues(1);
        float[] low = candles.getRawValues(2);
        float[] close = candles.getRawValues(3);
        
        for(int i=0, n=candles.getCandleCount(); i<n-14; i++) 
        {
            CandleDataPoint candle = candles.getCandle(i);
            float value = indicator.getValue(0, i);
            System.out.printf("%d,%s,%s,%.4f,%.4f,%.4f,%.4f,%.5f\n", i, candle.getDateTime().toLocalDate(), candle.getDateTime().toLocalTime(), open[i], high[i], low[i], close[i], value);
        }
    }
    
    public CandleCollection loadTestData() throws IOException
    {
        FXDataRequest request = new FXDataRequest();
        request.setDataSource(dataSource);
        request.setDate(startDate);
        request.setEndDate(startDate);
        request.setInstrument(instrument);
        request.setPeriod(period);
        return new CandleCollection(loader.loadCandlesForWeek(request));
    }
}
