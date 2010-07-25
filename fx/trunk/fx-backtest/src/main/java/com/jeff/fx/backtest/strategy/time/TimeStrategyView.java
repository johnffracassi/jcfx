package com.jeff.fx.backtest.strategy.time;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;
import org.joda.time.LocalTime;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.engine.TestEngine;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.common.CandleDataPoint;

@SuppressWarnings("serial")
public class TimeStrategyView extends JXPanel implements StrategyPropertyChangeListener {

	private static Logger log = Logger.getLogger(TimeStrategyChartController.class);

	private OrderBookController orderBook = new OrderBookController();
	private TimeStrategyChartController chart = new TimeStrategyChartController();
	private TimeStrategyOptimiserView optimiser = null;
	private List<CandleDataPoint> candles = Collections.<CandleDataPoint>emptyList();
	
	public TimeStrategyView() {

		setLayout(new BorderLayout());

		// create tabs for chart & order book
		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		// create views
		optimiser = new TimeStrategyOptimiserView();

		// add the tabs
		tabbedPane.add(chart.getView(), "Chart View");
		tabbedPane.add(orderBook.getView(), "Order Book");
		tabbedPane.add(optimiser, "Optimiser");

		// add the config panel at the bottom of the screen
		TimeStrategyConfigView pnlConfig = new TimeStrategyConfigView(this);
		pnlConfig.setPreferredSize(new Dimension(150, 150));
		add(pnlConfig, BorderLayout.SOUTH);
		
		loadCandles();
	}

	public void loadCandles() {

		try {
			candles = AppCtx.getDataManager().getCandles();
		} catch(Exception ex) {
			log.error("Error getting candles", ex);
		}
	}
	
	public void update() throws Exception {
		
		// get the parameters for the test run
		int openDayOfWeek = AppCtx.getInt("timeStrategy.openAt.dayOfWeek");
		LocalTime openTime = AppCtx.getTime("timeStrategy.openAt.time");
		int closeDayOfWeek = AppCtx.getInt("timeStrategy.closeAt.dayOfWeek");
		LocalTime closeTime = AppCtx.getTime("timeStrategy.closeAt.time");

		// perform the test, get the order book
		TestEngine te = new TestEngine();
		List<TimeStrategy> tests = new ArrayList<TimeStrategy>();
		tests.add(new TimeStrategy(1, openDayOfWeek, openTime, closeDayOfWeek, closeTime));
		te.executeTime(candles, tests);

		// update the controllers
		OrderBook orders = tests.get(0).getOrderBook();
		chart.update(orders);
		orderBook.update(orders);
	}
}
