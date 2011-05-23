package com.jeff.fx.backtest.chart.candle;

import com.jeff.fx.backtest.chart.CircleDrawer;
import com.jeff.fx.backtest.chart.DateTranslatingFormat;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.indicator.IndicatorMarker;
import org.jdesktop.swingx.JXFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
public class CandleChartFactory
{
    @Autowired
    private CandleListDatasetFactory candleListDatasetFactory;

    public void showChart(List<CandleDataPoint> candles, int pivotIdx)
    {
        JFreeChart chart = build(candles, pivotIdx);
        ChartPanel panel = new ChartPanel(chart);

        JXFrame frame = new JXFrame("Candles", false);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JFreeChart build(List<CandleDataPoint> candles, int pivotIdx)
    {
        DefaultHighLowDataset dataset = candleListDatasetFactory.create(candles);

        long start = candles.get(0).getDateTime().toDateTime().toDate().getTime() - candles.get(0).getPeriod().getInterval();
        long end = candles.get(candles.size()-1).getDateTime().toDateTime().toDate().getTime() + candles.get(candles.size()-1).getPeriod().getInterval();

        return createChart(dataset, pivotIdx, start, end);
    }

    private JFreeChart createChart(DefaultHighLowDataset dataset, int pivotIdx, long start, long end)
    {
    	NumberAxis timeAxis = new NumberAxis();
		NumberAxis valueAxis = new NumberAxis();
		XYPlot plot = new XYPlot(dataset, timeAxis, valueAxis, null);

		plot.setRenderer(new CandlestickRenderer());
		JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        timeAxis.setNumberFormatOverride(new DateTranslatingFormat(dataset));

        // set domain/category/x-axis bounds
        NumberAxis domainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
        domainAxis.setRange(start, end);
        domainAxis.setAutoRangeMinimumSize(start);

        // set range/value/y-axis bounds
        NumberAxis rangeAxis = (NumberAxis)chart.getXYPlot().getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRangeMinimumSize(0.005);

        IndicatorMarker marker = new IndicatorMarker(dataset.getXValue(0, pivotIdx), dataset.getYValue(0, pivotIdx), "Pivot");
        {
        	// add marker and label
            final CircleDrawer cd = new CircleDrawer(Color.red, new BasicStroke(1.0f), null);
            final XYAnnotation bestBid = new XYDrawableAnnotation(marker.getIndex(), marker.getValue(), 11, 11, cd);
            plot.addAnnotation(bestBid);
            final XYPointerAnnotation pointer = new XYPointerAnnotation(marker.getLabel(), marker.getIndex(), marker.getValue(), marker.getLabelLocation() * 3.0 * Math.PI / 4.0);
            pointer.setBaseRadius(35.0);
            pointer.setTipRadius(10.0);
            pointer.setFont(new Font("SansSerif", Font.PLAIN, 9));
            pointer.setPaint(Color.blue);
            pointer.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
            plot.addAnnotation(pointer);
        }

        return chart;
    }
}
