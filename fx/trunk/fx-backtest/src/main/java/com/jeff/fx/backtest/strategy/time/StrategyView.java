package com.jeff.fx.backtest.strategy.time;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.dataviewer.CandleDataController;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.orderbook.balancechart.BalanceChartController;
import com.jeff.fx.backtest.orderbook.report.OrderBookReportController;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserController;
import com.jeff.fx.common.CandleCollection;

@SuppressWarnings("serial")
public class StrategyView extends JXPanel implements StrategyPropertyChangeListener {

	private static Logger log = Logger.getLogger(BalanceChartController.class);

	private OrderBookReportController orderBook = new OrderBookReportController();
	private BalanceChartController chart = new BalanceChartController();
	private OptimiserController optimiser = new OptimiserController(this);
	private TimeStrategyConfigView config = null;
	private CandleDataController dataDisplay = new CandleDataController();
	private CandleCollection candles = new CandleCollection();
	
	public StrategyView() {

		log.debug("building new StrategyView");
		
		setLayout(new BorderLayout());

		// create tabs for chart & order book
		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		// add the tabs
		tabbedPane.add(optimiser.getView(), "Optimiser");
		tabbedPane.add(chart.getView(), "Chart View");
		tabbedPane.add(orderBook.getView(), "Order Book");
		tabbedPane.add(dataDisplay.getView(), "Candle Data");

		// add the config panel at the bottom of the screen
		config = new TimeStrategyConfigView(this);
		config.setPreferredSize(new Dimension(150, 150));
		add(config, BorderLayout.SOUTH);
		
		// setup listeners
		orderBook.addOrderSelectionListener(dataDisplay);
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
					dataDisplay.setCandles(candles);
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
		strategy.execute(candles, new IndicatorCache());

		// update the controllers
		OrderBook orders = strategy.getOrderBook();
		chart.update(orders);
		orderBook.update(orders);
	}
}
