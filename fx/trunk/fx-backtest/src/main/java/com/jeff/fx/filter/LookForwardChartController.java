package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.Period;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
public class LookForwardChartController
{
    private LookForwardChartView view;

    public LookForwardChartController()
    {
        view = new LookForwardChartView();
    }

    public void setCollections(List<List<CandleDataPoint>> collections, Period period) {

        view.setChart(MultiSeriesValueChart.createChart());

        XYPlot plot = (XYPlot) view.getChart().getPlot();

        buildPercentileChart(collections, period, plot);

        view.invalidate();
        view.validate();
        view.repaint();
    }

    private void buildFourPointChart(List<List<CandleDataPoint>> collections, XYPlot plot)
    {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        for (List<CandleDataPoint> collection : collections) {
            plot.setDataset(plot.getDatasetCount(), new FourPointValueDataset(collection));
            plot.setRenderer(plot.getDatasetCount(), renderer);
        }
    }

    private void buildSpaghettiChart(List<List<CandleDataPoint>> collections, XYPlot plot)
    {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        for (List<CandleDataPoint> collection : collections) {
            plot.setDataset(plot.getDatasetCount(), new AllPointsValueDataset(collection));
            plot.setRenderer(plot.getDatasetCount(), renderer);
        }
    }

    private void buildPercentileChart(List<List<CandleDataPoint>> collections, Period period, XYPlot plot)
    {
        int bands = 8;

        double[][] data = PercentileBuilder.buildTable(collections, bands);

        for (int p=1; p<=bands-1; p++)
        {
            XYDataset dataset = new PercentileValueDataset(data[p], period);
            plot.setDataset(p, dataset);
//            XYSplineRenderer renderer = new XYSplineRenderer();
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);

            double percentile = 100.0 * p / bands;
            int levels = bands / 2;
            int level = bands - Math.abs(bands / 2 - p) - bands/2;
            Color colour = new Color(192, (int)((192.0/levels)*(levels-level)), (int)((192.0/levels)*(levels-level)));

            if(p == bands / 2)
                colour = Color.BLUE;

            renderer.setSeriesPaint(0, colour);
            plot.setRenderer(p, renderer);
        }
    }

    public LookForwardChartView getView()
    {
        return view;
    }
}
