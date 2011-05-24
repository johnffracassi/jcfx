package com.jeff.fx.lfwd;

import com.jeff.fx.backtest.DatasetDefinitionPanel;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;
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
    private FilterController filterController;

    @Autowired
    private CandleDataStore loader;

    @Autowired
    private LookForwardFrame frame;

    @Autowired
    private DatasetDefinitionPanel datasetDefinitionPanel;

    private CandleCollection candles;
    private List<CandleDataPoint> startPoints;
    private int lookAheadDistance = 120;

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
        FXDataRequest request = datasetDefinitionPanel.getRequest();
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
        return loadCandles();
//        return (candles == null) ? loadCandles() : candles;
    }

    public void activate()
    {
        frame.setVisible(true);
    }
}
