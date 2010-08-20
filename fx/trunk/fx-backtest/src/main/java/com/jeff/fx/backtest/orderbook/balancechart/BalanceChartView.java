package com.jeff.fx.backtest.orderbook.balancechart;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.jeff.fx.gui.GUIUtil;

public class BalanceChartView extends JXPanel {
	
	private static final long serialVersionUID = -8318860518282273899L;

	private ChartPanel chartPanel = null;
	private JFreeChart chart = null;
	private TimeSeries timeSeries = null;
	private TimeSeriesCollection dataset = null;
	
	public BalanceChartView() {
		
		setLayout(new BorderLayout());
		
		try {
			chartPanel = new ChartPanel(createChart());
			add(GUIUtil.frame("Balance Chart", chartPanel), BorderLayout.CENTER);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void update(BalanceChartModel model) {
		dataset.removeSeries(0);
		dataset.addSeries(model.getChartData());
	}

	/**
	 * Create a new chart
	 */
	private JFreeChart createChart() throws Exception {
		
		timeSeries = new TimeSeries("Balance", Minute.class);
		dataset = new TimeSeriesCollection(timeSeries);
		
		// build the new chart
        chart = ChartFactory.createTimeSeriesChart(
            null, "Date", "Balance", dataset, false, false, false
        );
        
		chart.setBackgroundPaint(Color.white);
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		final XYItemRenderer renderer = plot.getRenderer();
		if (renderer instanceof StandardXYItemRenderer) {
			renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		}

		final DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yy"));
		
		return chart;
	}
}
