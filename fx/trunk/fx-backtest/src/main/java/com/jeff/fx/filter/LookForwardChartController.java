package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.springframework.stereotype.Component;

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

        view.setChart(MultiSeriesValueChart.createChart());

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);

        XYPlot plot = (XYPlot) view.getChart().getPlot();

        for (List<CandleDataPoint> collection : collections) {
//            plot.setDataset(plot.getDatasetCount(), new FourPointValueDataset(collection));
            plot.setDataset(plot.getDatasetCount(), new AllPointsValueDataset(collection));
            plot.setRenderer(plot.getDatasetCount(), renderer);
        }
    }

    public LookForwardChartView getView()
    {
        return view;
    }
}
