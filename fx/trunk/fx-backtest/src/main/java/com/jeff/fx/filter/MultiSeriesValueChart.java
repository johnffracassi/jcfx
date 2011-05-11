package com.jeff.fx.filter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class MultiSeriesValueChart extends JPanel {

	public static ChartPanel createChart()
    {
        final JFreeChart chart = build();
		return new ChartPanel(chart);
	}
    
    private static JFreeChart build()
    {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                null,    // chart title
                null,    // x axis label
                null,    // y axis label
                new XYSeriesCollection(), // data
                PlotOrientation.VERTICAL,
                false,   // include legend
                false,   // tooltips
                false    // urls
        );

        return chart;
    }
}