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
public class LookForwardController {

    @Autowired
    private LookForwardChartController chartController;

    @Autowired
    private LookForwardDatasetController datasetController;

    @Autowired
    private CandleDataStore loader;

    @Autowired
    private CandleFilterModelEvaluator evaluator;


    public LookForwardController()
    {
    }

    public void run()
    {
        try
        {
            CandleCollection candles = loadTestData();
            List<CandleDataPoint> startPoints = findStartPoints(candles, new TimeOfWeek("Mo0845"));
            List<List<CandleDataPoint>> collections = findIndividualPriceLines(candles, startPoints, 32);

            datasetController.setCollections(collections);
            chartController.setCollections(collections);
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

    private List<CandleDataPoint> findStartPoints(CandleCollection candles, TimeOfWeek time)
    {
        List<CandleDataPoint> list = new LinkedList<CandleDataPoint>();

        long stime = System.nanoTime();

        CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);

        for(int c=0; c<candles.getCandleCount(); c++)
        {
            model.setIndex(c);
            CandleDataPoint candle = model.getCandles().getCandle(c);
            boolean result = new TimeOfWeek(candle.getDateTime()).equals(time);

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
        LocalDate startDate = new LocalDate(2008, 1, 1);
        LocalDate endDate = new LocalDate(2011, 4, 29);

        FXDataRequest request = new FXDataRequest();
        request.setDataSource(dataSource);
        request.setDate(startDate);
        request.setEndDate(endDate);
        request.setInstrument(instrument);
        request.setPeriod(period);
        return loader.loadCandles(request).getCandles();
    }

}
