package com.jeff.fx.filter;

import com.jeff.fx.backtest.chart.IndicatorDataset;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.*;
import com.jeff.fx.datastore.CandleDataStore;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class LookForwardChartController
{
    private LookForwardChartView view;

    public LookForwardChartController()
    {
        view = new LookForwardChartView();
    }

    public void setCollections(List<List<CandleDataPoint>> collections) {

        view.setChart(FourPointValueChart.createChart(collections));

        XYPlot plot = (XYPlot) view.getChart().getPlot();
        for (List<CandleDataPoint> collection : collections) {
            plot.setDataset(plot.getDatasetCount(), new FourPointValueDataset(collection));
            plot.setRenderer(plot.getDatasetCount(), new StandardXYItemRenderer());
        }
    }

    public LookForwardChartView getView() {
        return view;
    }
}
