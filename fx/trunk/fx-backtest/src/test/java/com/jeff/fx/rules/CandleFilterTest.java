package com.jeff.fx.rules;

import java.io.IOException;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.overlay.SimpleMovingAverage;
import com.jeff.fx.lookforward.CandleFilter;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.business.AbstractFXNode;
import com.jeff.fx.rules.business.TimeOfWeekNodeFactory;
import com.jeff.fx.rules.logic.AndNode;

@Component("candleFilterTest")
public class CandleFilterTest {

	@Autowired
	private CandleDataStore loader;

	private FXDataSource dataSource = FXDataSource.Forexite;
	private Instrument instrument = Instrument.GBPUSD;
	private Period period = Period.OneMin;
	private LocalDate startDate = new LocalDate(2010, 10, 20);

	public static void main(String[] args) throws Exception 
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		CandleFilterTest app = (CandleFilterTest) ctx.getBean("candleFilterTest");
		app.run();
	}
    
    public void run() throws IOException {

        final CandleCollection candles = loadTestData();
        
        final SimpleMovingAverage sma = new SimpleMovingAverage(28, CandleValueModel.Typical);
        sma.calculate(candles);
        
        AbstractFXNode smaAboveTp = new AbstractFXNode() {
            @Override
            public boolean evaluate(CandleFilterModel model)
            {
                boolean result = false;
                int idx = model.getIndex();
                
                float smaVal = sma.getValue(idx);
                float tp = model.getCandles().getPrice(idx, CandleValueModel.Typical);
                result = smaVal > (tp * 1.0010);
                
                return result;
            }
        };
        
        Node<CandleFilterModel> timeRangeNode = TimeOfWeekNodeFactory.timeOfWeekIsBetween(new TimeOfWeek(TimeOfWeek.MONDAY, 02, 00), new TimeOfWeek(TimeOfWeek.MONDAY, 06, 00));

        Node<CandleFilterModel> rootNode = new AndNode<CandleFilterModel>(timeRangeNode, smaAboveTp);
        
        CandleFilter filter = new CandleFilter();
        List<CandleDataPoint> filtered = filter.apply(rootNode, candles);
        
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
