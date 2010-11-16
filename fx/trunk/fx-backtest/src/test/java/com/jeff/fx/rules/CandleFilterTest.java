package com.jeff.fx.rules;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.lookforward.CandleFilterModelEvaluator;

@Component("candleFilterTest")
public class CandleFilterTest {

	@Autowired
	private CandleDataStore loader;

	@Autowired
	private CandleFilterModelEvaluator evaluator;
	
	private FXDataSource dataSource = FXDataSource.Forexite;
	private Instrument instrument = Instrument.GBPUSD;
	private Period period = Period.OneMin;
	private LocalDate startDate = new LocalDate(2010, 10, 20);

	public static void main(String[] args) throws Exception 
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		CandleFilterTest app = (CandleFilterTest) ctx.getBean("candleFilterTest");
		app.run();
	}
    
    public void run() throws IOException {

        final CandleCollection candles = loadTestData();
        
        CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);

        for(int i=0; i<10; i++)
        {
            model.setIndex(i);
            double close = evaluator.evaluate(model, "candle.close", double.class);
            double sma14 = evaluator.evaluate(model, "ind['sma(1,Typical)'][0] + 0.0000", double.class);
            System.out.println(close + " > " + sma14 + " = " + Operand.ge.evaluate(close, sma14));
        }
        
//        TimeRangeNode trn = new TimeRangeNode();
//        trn.setTo(new TimeOfWeek(TimeOfWeek.MONDAY, 0, 0));
//        trn.update();
//
        //        CandleFilter filter = new CandleFilter();
//        List<CandleDataPoint> filtered = filter.apply(trn, candles);
//        
//        System.out.println("found " + filtered.size() + " candles");
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
