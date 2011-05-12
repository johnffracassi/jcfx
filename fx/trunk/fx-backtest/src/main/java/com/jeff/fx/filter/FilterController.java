package com.jeff.fx.filter;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.indicator.Indicator;
import com.jeff.fx.indicator.overlay.AbstractMovingAverage;
import com.jeff.fx.indicator.overlay.ExponentialMovingAverage;
import com.jeff.fx.indicator.overlay.SimpleMovingAverage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumMap;
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
    private CandleValueModel cvm = CandleValueModel.Close;

    public FilterController()
    {
        view = new FilterView();

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
            String expression = view.getExpression().trim();
            TimeOfWeek time = view.getSlider().getTimeOfWeek();

            CandleCollection candles = lookForwardController.getCandles();
            List<CandleDataPoint> startPoints;

            if(expression.length() > 3)
                startPoints = findStartPoints(candles, expression);
            else
                startPoints = findStartPoints(candles, time);

            lookForwardController.updateStartPoints(startPoints);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
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

            if(evaluator.evaluate(model, expression, boolean.class))
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

        AbstractMovingAverage[] ema = new AbstractMovingAverage[4];
        for(int i=0; i<ema.length; i++)
        {
            ema[i] = (AbstractMovingAverage)indicatorCache.calculate(new SimpleMovingAverage((int)Math.pow(2, i+3), CandleValueModel.Close), candles);
        }

        CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);
        for(int c=0; c<candles.getCandleCount(); c++)
        {
            model.setIndex(c);
            CandleDataPoint candle = model.getCandles().getCandle(c);

            if(cvm.evaluate(candle) > 0)
            {
                boolean result = new TimeOfWeek(candle.getDateTime()).equals(time);

                if(result)
                {
                    boolean ok = true;
                    for(int i=0; i<ema.length-1; i++)
                    {
                        if(ema[i].getValue(c) < ema[i+1].getValue(c))
                        {
                            ok = false;
                        }
                        if(ema[i].getDirection(c) != 1)
                        {
                            ok = false;
                        }
                    }

                    if(ok)
                    {
                        list.add(candle);
                    }
                }

            }
        }

        System.out.printf("Completed in %.3fs (selected %d of %d candles)", (System.nanoTime() - stime) / 1000000000.0, list.size(), candles.getCandleCount());

        return list;
    }
}
