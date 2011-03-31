package com.jeff.fx.rules;

import java.io.IOException;
import java.util.List;

import org.joda.time.LocalDate;
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
import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.lookforward.CandleFilterProcessor;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.lookforward.CandleFilterModelEvaluator;
import com.jeff.fx.rules.business.ELNode;
import com.jeff.fx.rules.business.TimeRangeNode;

@Component("candleFilterTest")
public class CandleFilterTest {

	@Autowired
	private CandleDataStore loader;

	@Autowired
	private CandleFilterProcessor processor;
	
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
        ELNode elNode = new ELNode("idx", Operand.lt, "25.0");
        List<CandleDataPoint> filtered = processor.apply(elNode, candles);
        
//        CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);
//        Operand op = Operand.ge;
//        for(int i=0; i<10; i++)
//        {
//            model.setIndex(i);
//            double price = evaluator.visit(model, "price", double.class);
//            double sma = evaluator.visit(model, "ind['sma(1,Typical)'][0] + 0.0000", double.class);
//            System.out.printf("%1.5f %s %1.5f = %s \n", price, op.getLabel(), sma, op.visit(price, sma));
//        }
        
        System.out.println("found " + filtered.size() + " candles");
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
