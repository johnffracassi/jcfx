package com.jeff.fx.filter;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

            SimpleCandleFilter[] filters = new SimpleCandleFilter[] {
                    new CandlePatternFilter(view.getPattern())
            };

            startPoints = findStartPoints(candles, filters);

            lookForwardController.updateStartPoints(startPoints);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private List<CandleDataPoint> findStartPoints(CandleCollection candles, SimpleCandleFilter[] filters)
    {
        List<CandleDataPoint> list = new LinkedList<CandleDataPoint>();

        long stime = System.nanoTime();

        CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);
        for(int c=0; c<candles.getCandleCount(); c++)
        {
            model.setIndex(c);
            CandleDataPoint candle = model.getCandles().getCandle(c);

            if(cvm.evaluate(candle) > 0)
            {
                boolean include = true;

                for (SimpleCandleFilter filter : filters) {
                    if(filter.filter(model, c))
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

        System.out.printf("Completed in %.3fs (selected %d of %d candles)", (System.nanoTime() - stime) / 1000000000.0, list.size(), candles.getCandleCount());

        return list;
    }
}
