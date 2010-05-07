package com.jeff.fx.backtest;

import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.XYDataset;

import com.jeff.fx.common.CandleDataPoint;

public class CandleChart extends JPanel {

	public static ChartPanel createChart(String title, List<CandleDataPoint> candles) {
        final DefaultHighLowDataset dataset = createDataset(candles);
        final JFreeChart chart = createChart(dataset);
		return new ChartPanel(chart);
	}
    
    private static DefaultHighLowDataset createDataset(List<CandleDataPoint> candles) {
    	double[][] data = new double[5][candles.size()];
    	Date[] dates = new Date[candles.size()];
    	
    	int idx = 0;
    	for(CandleDataPoint candle : candles) {
    		dates[idx] = candle.getDate().toDateTime().toDate();
    		data[0][idx] = candle.getSellOpen();
    		data[1][idx] = candle.getSellHigh();
    		data[2][idx] = candle.getSellLow();
    		data[3][idx] = candle.getSellClose();
    		data[4][idx] = 0.0;
    		idx++;
    	}
    	
    	return new DefaultHighLowDataset("Data", dates, data[1], data[2], data[0], data[3], data[4]);
    }

    /**
     * Creates a sample chart.
     * @param dataset  a dataset.
     * @return a sample chart.
     */
    private static JFreeChart createChart(final DefaultHighLowDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createCandlestickChart(
            "OHLC Demo",
            "Time", 
            "Value",
            dataset, 
            true
        );
        
        final DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
        axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);

        final XYDataset dataset2 = MovingAverage.createMovingAverage(dataset, "SMA(20)", 20 * 15 * 60 * 1000L, 0L);
        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, new StandardXYItemRenderer());
        
        final XYDataset dataset3 = MovingAverage.createMovingAverage(dataset, "SMA(50)", 50 * 15 * 60 * 1000L, 0L);
        plot.setDataset(2, dataset3);
        plot.setRenderer(2, new StandardXYItemRenderer()); 
        
        final XYDataset dataset4 = MovingAverage.createMovingAverage(dataset, "SMA(200)", 200 * 15 * 60 * 1000L, 0L);
        plot.setDataset(3, dataset4);
        plot.setRenderer(3, new StandardXYItemRenderer());
        
        return chart;
    }
}