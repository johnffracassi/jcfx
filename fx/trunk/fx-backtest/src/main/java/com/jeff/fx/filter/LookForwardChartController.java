package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.Period;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.PaintUtilities;
import org.jfree.util.StrokeList;
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

    public void setCollections(List<List<CandleDataPoint>> collections) {

        view.setChart(MultiSeriesValueChart.createChart());

        XYPlot plot = (XYPlot) view.getChart().getPlot();

        buildPercentileChart(collections, plot);

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

    private void buildPercentileChart(List<List<CandleDataPoint>> collections, XYPlot plot)
    {
        double[][] data = PercentileBuilder.buildTable(collections);

        for (int p=0; p<=10; p++)
        {
            XYDataset dataset = new PercentileValueDataset(p*10, data[p], Period.OneMin);
            plot.setDataset(p, dataset);
            plot.getRendererForDataset(dataset).setPaint(new Color(255, p * 22, p * 22));
        }
    }

    public LookForwardChartView getView()
    {
        return view;
    }
}
