package com.jeff.fx.filter;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.Indicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Component;

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
            CandleCollection candles = lookForwardController.getCandles();
            List<CandleDataPoint> startPoints;

            List<SimpleCandleFilter> filters = new ArrayList<SimpleCandleFilter>();

            for(int i=0; i<5; i++)
                if(view.getPattern(i) != null)
                    filters.add(new CandlePatternFilter(view.getPattern(i), i));

            if(view.isTimeEnabled())
                filters.add(new CandleTimeFilter(view.getSlider().getTimeOfWeek()));

            if(view.getExpression() != null)
                filters.add(new ExpressionFilter(view.getExpression()));

            startPoints = findStartPoints(candles, filters);

            lookForwardController.updateStartPoints(startPoints);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private int runningThreads = 0;

    private List<CandleDataPoint> findStartPoints(final CandleCollection candles, final List<SimpleCandleFilter> filters)
    {
        final int threads = 6;
        final int cpt = candles.getCandleCount() / threads;

        final IndicatorCache indicatorCache = new IndicatorCache();
        final List<CandleDataPoint> startPoints = new LinkedList<CandleDataPoint>();

        final long stime = System.nanoTime();
        for(int t=0; t<threads; t++)
        {
            runningThreads ++;
            final int threadIdx = t;
            new Thread()
            {
                public void run()
                {
                    System.out.printf("Thread #%d started (%d to %d) %n", threadIdx, threadIdx*cpt, (threadIdx+1)*cpt);
                    CandleFilterModel model = new CandleFilterModel(candles, indicatorCache, evaluator);
                    List<CandleDataPoint> list = new LinkedList<CandleDataPoint>();

                    for(int c=threadIdx*cpt; c<(threadIdx+1)*cpt; c++)
                    {
                        model.setIndex(c);
                        CandleDataPoint candle = model.getCandles().getCandle(c);

                        if(cvm.evaluate(candle) > 0)
                        {
                            boolean include = true;

                            for (SimpleCandleFilter filter : filters)
                            {
                                if(filter.filter(model))
                                {
                                    include = false;
                                }
                            }

                            if(include)
                            {
                                list.add(candle);
                            }
                        }
                    }

                    synchronized(startPoints)
                    {
                        startPoints.addAll(list);
                        System.out.printf("Thread #%d, completed in %.3fs (selected %d of %d candles) %n", threadIdx, (System.nanoTime() - stime) / 1000000000.0, list.size(), cpt);
                        runningThreads --;
                    }
                }
            }.start();
        }

        while(runningThreads > 0)
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        System.out.printf("All threads, completed in %.3fs (selected %d of %d candles) %n", (System.nanoTime() - stime) / 1000000000.0, startPoints.size(), cpt);

        return startPoints;
    }
}
