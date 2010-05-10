package com.jeff.fx.backtest;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.JXLabel;
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

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.engine.SimpleStrategy;
import com.jeff.fx.backtest.engine.TestEngine;
import com.jeff.fx.common.CandleDataPoint;

public class SimpleStrategyChartPanel extends JXPanel {

	private JXLabel lbl = new JXLabel("chart panel");
	private List<CandleDataPoint> candles = null;
	private ChartPanel chartPanel = null;

	public SimpleStrategyChartPanel() {

		setLayout(new BorderLayout());
		
		try {
			loadCandleDataset();
			chartPanel = new ChartPanel(createChart());
			add(chartPanel, BorderLayout.CENTER);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		AppCtx.register(NewSimpleStrategyChartEvent.class, new FXActionEventListener() {
			public void event(FXActionEvent ev) {
				lbl.setText(AppCtx.get("simpleStrategy.openFor") + " / " + AppCtx.get("simpleStrategy.closedFor"));
			}
		});
		
		SimpleStrategyConfigPanel pnlConfig = new SimpleStrategyConfigPanel(this);
		pnlConfig.setPreferredSize(new Dimension(150, 150));
		add(pnlConfig, BorderLayout.SOUTH);
	}

	
	protected void update() throws Exception {
		chartPanel.setChart(createChart());
	}
	
	/**
	 * Update the candle data set
	 */
	private void loadCandleDataset() throws Exception {
		candles = AppCtx.getDataManager().getCandles();
	}

	/**
	 * Create a new chart
	 */
	private JFreeChart createChart() throws Exception {
		
		// build the new chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Sample Chart", "Date", "Value", updateChartDataset(), true, true, false
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
	
	/**
	 * 
	 */
	private TimeSeriesCollection updateChartDataset() {
		
		// get the parameters for the test run
		double openFor = AppCtx.get("simpleStrategy.openFor");
		double closedFor = AppCtx.get("simpleStrategy.closedFor");
		
		// perform the test, get the order book
		TestEngine te = new TestEngine();
		List<SimpleStrategy> tests = new ArrayList<SimpleStrategy>();
		tests.add(new SimpleStrategy(1, new double[] { openFor, closedFor }));
		te.execute(candles, tests);
		OrderBook orderBook = tests.get(0).getOrderBook();
		
		// build the series
		TimeSeriesCollection dataset = createTimeSeriesDataset(orderBook);
		
		return dataset;
	}
	
	/**
	 * Create the data for the chart
	 */
	private TimeSeriesCollection createTimeSeriesDataset(OrderBook orderBook) {

		TimeSeries ts = new TimeSeries("Balance", Minute.class);

		List<BTOrder> orders = orderBook.getClosedOrders();

		double balance = 0.0;
		for (BTOrder order : orders) {
			balance += order.getProfit();
			ts.addOrUpdate(new Minute(order.getCloseTime().toDateTime().toDate()), balance);
		}

		TimeSeriesCollection tsc = new TimeSeriesCollection();
		tsc.addSeries(ts);

		return tsc;
	}
}
