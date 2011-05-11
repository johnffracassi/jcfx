package com.jeff.fx.filter;

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;

public class LookForwardChartView extends JXPanel
{
    private JFreeChart chart;

    public LookForwardChartView()
    {
        setLayout(new BorderLayout());
    }

    public JFreeChart getChart()
    {
        return chart;
    }

    public void setChart(ChartPanel chart)
    {
        removeAll();
        add(chart, BorderLayout.CENTER);
        this.chart = chart.getChart();
    }
}
