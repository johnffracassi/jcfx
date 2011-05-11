package com.jeff.fx.filter;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.CandleWeek;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.joda.time.LocalDateTime;

import javax.swing.*;
import java.util.List;

public class FourPointValueChart extends JPanel {

	public static ChartPanel createChart(List<List<CandleDataPoint>> collections) {
		
        final JFreeChart chart = build();
		return new ChartPanel(chart);
	}
    
    private static JFreeChart build() {
    	
    	XYSeries s1 = new XYSeries("Four Point Value Chart");

    	float high = Float.MIN_VALUE;
    	float low = Float.MAX_VALUE;
    	
    	XYSeriesCollection dataset = new XYSeriesCollection();
        
        final JFreeChart chart = ChartFactory.createXYLineChart(
                null,    // chart title
                null,    // x axis label
                null,    // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false,   // include legend
                true,    // tooltips
                false    // urls
            ); 

        return chart;
    }
}