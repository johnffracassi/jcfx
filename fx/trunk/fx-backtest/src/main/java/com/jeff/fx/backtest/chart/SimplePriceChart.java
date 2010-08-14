package com.jeff.fx.backtest.chart;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.joda.time.LocalDateTime;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleWeek;

@SuppressWarnings("serial")
public class SimplePriceChart extends JPanel {

	/**
	 * Create a panel with the chart in it
	 * @param title
	 * @param candles
	 * @param start
	 * @param end
	 * @return
	 */
	public static ChartPanel createChart(String title, CandleCollection candles, LocalDateTime start, LocalDateTime end) {
		
        final JFreeChart chart = build(candles, start, end);
		return new ChartPanel(chart);
	}
    
	/**
	 * Create the data for the chart
	 * @param candles
	 * @return
	 */
    private static JFreeChart build(CandleCollection candles, LocalDateTime start, LocalDateTime end) {
    	
    	XYSeries s1 = new XYSeries("Typical Price");
    	CandleWeek cw = candles.getCandleWeek(start.toLocalDate());
    	
    	int startIdx = cw.getCandleIndex(start);
    	int endIdx = cw.getCandleIndex(end);
    	
    	float high = Float.MIN_VALUE;
    	float low = Float.MAX_VALUE;
    	
    	for(int idx = startIdx; idx < endIdx; idx++) {
    		int pt = idx - startIdx;
    		float price = cw.getTypicalPrice(idx);
    		
    		if(price < low)
    			low = price;
    		
    		if(price > high) 
    			high = price;
    		
    		s1.add(pt, price);
    	}
    	
    	XYSeriesCollection dataset = new XYSeriesCollection(s1);
        
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Price Chart",      // chart title
                "Date",                   // x axis label
                "Price",                  // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                false,                     // include legend
                true,                     // tooltips
                false                     // urls
            ); 

        chart.getXYPlot().getRangeAxis().setRange(low * 0.9995, high * 1.0005);
        
        return chart;
    }
}