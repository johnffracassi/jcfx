package com.jeff.fx.filter;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.*;
import com.jeff.fx.datastore.CandleDataStore;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class LookForwardChartController
{
    @Autowired
    private CandleDataStore loader;
    
    @Autowired
    private CandleFilterModelEvaluator evaluator;
    
    private LookForwardChartView view;
    
    public LookForwardChartController()
    {
        view = new LookForwardChartView();
    }

    public LookForwardChartView getView()
    {
        return view;
    }

    public void createChart()
    {
        String expression = "tow == 'Mo0900'";

        try
        {
            CandleCollection candles = loadTestData();
            List<CandleDataPoint> startPoints = findStartPoints(candles, expression);
            List<List<CandleDataPoint>> collections = findIndividualPriceLines(candles, startPoints, 32);
            
            for(List<CandleDataPoint> collection : collections)
            {
                System.out.println(collection);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private List<List<CandleDataPoint>> findIndividualPriceLines(CandleCollection candles, List<CandleDataPoint> startPoints, int lookAheadDistance)
    {
        List<List<CandleDataPoint>> collections = new ArrayList<List<CandleDataPoint>>(startPoints.size());
        
        for(CandleDataPoint startPoint : startPoints)
        {
            int idx = candles.getCandleIndex(startPoint.getDateTime());
            List<CandleDataPoint> collection = candles.getCandles(idx, lookAheadDistance);
            collections.add(collection);
        }
        
        return collections;
    }
    
    private List<CandleDataPoint> findStartPoints(CandleCollection candles, String expression)
    {
        List<CandleDataPoint> list = new LinkedList<CandleDataPoint>();
        
        long stime = System.nanoTime();

        CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);

        for(int c=0; c<candles.getCandleCount(); c++)
        {
            model.setIndex(c);
            CandleDataPoint candle = model.getCandles().getCandle(c);
            boolean result = evaluator.evaluate(model, expression, boolean.class);

            if(result)
            {
                list.add(candle);
            }
        }

        System.out.printf("Completed in %.3fs (selected %d of %d candles)", (System.nanoTime() - stime) / 1000000000.0, list.size(), candles.getCandleCount());
        
        return list;
    }
    
    private CandleCollection loadTestData() throws IOException
    {
        FXDataSource dataSource = FXDataSource.Forexite;
        Instrument instrument = Instrument.GBPUSD;
        Period period = Period.FifteenMin;
        LocalDate startDate = new LocalDate(2010, 1, 5);
        LocalDate endDate = new LocalDate(2011, 5, 6);

        FXDataRequest request = new FXDataRequest();
        request.setDataSource(dataSource);
        request.setDate(startDate);
        request.setEndDate(endDate);
        request.setInstrument(instrument);
        request.setPeriod(period);
        return loader.loadCandles(request).getCandles();
    }
}
