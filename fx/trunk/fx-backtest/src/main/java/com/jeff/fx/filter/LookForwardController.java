package com.jeff.fx.filter;

import com.jeff.fx.common.*;
import com.jeff.fx.datastore.CandleDataStore;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LookForwardController {

    @Autowired
    private LookForwardChartController chartController;

    @Autowired
    private LookForwardDatasetController datasetController;

    @Autowired
    private CandleDataStore loader;

    private CandleCollection candles;
    private List<CandleDataPoint> startPoints;
    private int lookAheadDistance = 64;

    public LookForwardController()
    {
    }

    public void updateDataset(List<List<CandleDataPoint>> collections, Period period)
    {
        datasetController.setCollections(collections);
        chartController.setCollections(collections, period);
    }

    public void updateStartPoints(List<CandleDataPoint> startPoints)
    {
        this.startPoints = startPoints;
        Period period = null;

        List<List<CandleDataPoint>> collections = new ArrayList<List<CandleDataPoint>>(startPoints.size());
        for(CandleDataPoint startPoint : startPoints)
        {
            if(period == null && startPoint != null && startPoint.getPeriod() != null)
                period = startPoint.getPeriod();

            int idx = candles.getCandleIndex(startPoint.getDateTime());
            List<CandleDataPoint> collection = candles.getCandles(idx, lookAheadDistance);
            collections.add(collection);
        }

        updateDataset(collections, period);
    }

    private CandleCollection loadTestData() throws IOException
    {
        FXDataSource dataSource = FXDataSource.Forexite;
        Instrument instrument = Instrument.GBPUSD;
        Period period = Period.FifteenMin;
        LocalDate startDate = new LocalDate(2002, 1, 5);
        LocalDate endDate = new LocalDate(2011, 4, 29);

        FXDataRequest request = new FXDataRequest();
        request.setDataSource(dataSource);
        request.setDate(startDate);
        request.setEndDate(endDate);
        request.setInstrument(instrument);
        request.setPeriod(period);
        return loader.loadCandles(request).getCandles();
    }

    public CandleCollection loadCandles()
    {
        try
        {
            candles = loadTestData();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return candles;
    }

    public CandleCollection getCandles()
    {
        return (candles == null) ? loadCandles() : candles;
    }
}
