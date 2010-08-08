package com.jeff.fx.backtest.strategy.time;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collections;
import java.util.Map;

import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;

@SuppressWarnings("serial")
public class StrategyView extends JXPanel implements StrategyPropertyChangeListener {

	private static Logger log = Logger.getLogger(TimeStrategyChartController.class);

	private OrderBookController orderBook = new OrderBookController();
	private TimeStrategyChartController chart = new TimeStrategyChartController();
	private OptimiserController optimiser = new OptimiserController(this);
	private TimeStrategyConfigView config = null;
	private CandleCollection candles = new CandleCollection();
	
	public StrategyView() {

		setLayout(new BorderLayout());

		// create tabs for chart & order book
		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		// add the tabs
		tabbedPane.add(chart.getView(), "Chart View");
		tabbedPane.add(orderBook.getView(), "Order Book");
		tabbedPane.add(optimiser.getView(), "Optimiser");

		// add the config panel at the bottom of the screen
		config = new TimeStrategyConfigView(this);
		config.setPreferredSize(new Dimension(150, 150));
		add(config, BorderLayout.SOUTH);
	}

	public void initialise() {
		loadCandles();
	}
	
	private void loadCandles() {
		new SwingWorker<Object,Object>() {
			protected Object doInBackground() throws Exception {
				try {
					candles = AppCtx.getDataManager().getCandles();
					optimiser.setCandles(candles);
				} catch(Exception ex) {
					log.error("Error getting candles", ex);
				}
				return null;
			}
		}.execute();
	}
	
	public void setParams(Map<String,Object> params) {
		config.setParams(params);
	}
	
	public void update() throws Exception {
		
		// perform the test, get the order book
		TimeStrategy strategy = new TimeStrategy(1, config.getParams());
		strategy.execute(candles);

		// update the controllers
		OrderBook orders = strategy.getOrderBook();
		chart.update(orders);
		orderBook.update(orders);
	}
}
