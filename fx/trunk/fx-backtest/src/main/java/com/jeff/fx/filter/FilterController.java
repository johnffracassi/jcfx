package com.jeff.fx.filter;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.overlay.ExponentialMovingAverage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class FilterController
{
    @Autowired
    private LookForwardController lookForwardController;

    @Autowired
    private CandleFilterModelEvaluator evaluator;

    private FilterView view;
    private IndicatorCache indicatorCache;

    public FilterController()
    {
        view = new FilterView();
        view.getSlider().addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
            }
        });

        view.getBtnUpdate().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                update();
            }
        });

        indicatorCache = new IndicatorCache();
    }

    public FilterView getView()
    {
        return view;
    }

    private void update()
    {
        try
        {
            TimeOfWeek time = view.getSlider().getTimeOfWeek();

            CandleCollection candles = lookForwardController.getCandles();
            List<CandleDataPoint> startPoints = findStartPoints(candles, time);
            List<List<CandleDataPoint>> collections = findIndividualPriceLines(candles, startPoints, 32);
            lookForwardController.updateDataset(collections);
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

        Indicator ema8 = indicatorCache.calculate(new ExponentialMovingAverage(14, CandleValueModel.Open), candles);
        Indicator ema32 = indicatorCache.calculate(new ExponentialMovingAverage(32, CandleValueModel.Open), candles);
        Indicator ema128 = indicatorCache.calculate(new ExponentialMovingAverage(128, CandleValueModel.Open), candles);

        CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);

        for(int c=0; c<candles.getCandleCount(); c++)
        {
            model.setIndex(c);
            CandleDataPoint candle = model.getCandles().getCandle(c);
            boolean result = evaluator.evaluate(model, expression, boolean.class);

            if(result)
            {
                if(ema8.getValue(0, c) > ema32.getValue(0, c))
                {
                    if(ema32.getValue(0, c) > ema128.getValue(0, c))
                    {
                        list.add(candle);
                    }
                }
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
}
