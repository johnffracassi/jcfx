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
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.engine.SimpleStrategy;
import com.jeff.fx.backtest.engine.TestEngine;
import com.jeff.fx.common.CandleDataPoint;

public class SimpleStrategyChartPanel extends JXPanel {

	private JXLabel lbl = new JXLabel("chart panel");
	private List<CandleDataPoint> candles = null;
	private TimeSeries timeSeries = null;
	private ChartPanel chartPanel = null;
	private JFreeChart chart = null;

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
		
		timeSeries = updateChartDataset();
		TimeSeriesCollection tsc = new TimeSeriesCollection(timeSeries);
		
		// build the new chart
        chart = ChartFactory.createTimeSeriesChart(
            "Balance Chart", "Date", "Value", tsc, true, true, false
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
	private TimeSeries updateChartDataset() {
		
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
		TimeSeries dataset = createTimeSeriesDataset(orderBook);
		return dataset;
	}
	
	/**
	 * Create the data for the chart
	 */
	private TimeSeries createTimeSeriesDataset(OrderBook orderBook) {

		TimeSeries ts = new TimeSeries("Balance", Minute.class);

		List<BTOrder> orders = orderBook.getClosedOrders();

		double balance = 0.0;
		for (BTOrder order : orders) {
			balance += order.getProfit();
			ts.addOrUpdate(new Minute(order.getCloseTime().toDateTime().toDate()), balance);
		}

		return ts;
	}
}

class TranslatingXYDataset extends AbstractXYDataset implements XYDataset, DatasetChangeListener {

	private XYDataset underlying;
	private double translate;

	public TranslatingXYDataset(XYDataset underlying) {
		this.underlying = underlying;
		this.underlying.addChangeListener(this);
		this.translate = 0.0;
	}

	public double getTranslate() {
		return this.translate;
	}

	public void setTranslate(double t) {
		this.translate = t;
		fireDatasetChanged();
	}

	public int getItemCount(int series) {
		return this.underlying.getItemCount(series);
	}

	public double getXValue(int series, int item) {
		return this.underlying.getXValue(series, item) + translate;
	}

	public Number getX(int series, int item) {
		return new Double(getXValue(series, item));
	}

	public Number getY(int series, int item) {
		return new Double(getYValue(series, item));
	}

	public double getYValue(int series, int item) {
		return this.underlying.getYValue(series, item);
	}

	public int getSeriesCount() {
		return this.underlying.getSeriesCount();
	}

	public Comparable getSeriesKey(int series) {
		return underlying.getSeriesKey(series);
	}

	public void datasetChanged(DatasetChangeEvent event) {
		this.fireDatasetChanged();
	}
}