package com.jeff.fx.backtest.strategy.time;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.orderbook.OrderBookController;
import com.jeff.fx.backtest.orderbook.balancechart.BalanceChartController;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.backtest.strategy.optimiser.OptimiserController;
import com.jeff.fx.common.CandleCollection;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

@SuppressWarnings("serial")
public class StrategyView extends JXPanel implements StrategyPropertyChangeListener {

	private static Logger log = Logger.getLogger(BalanceChartController.class);

	private OrderBookController orderBook = new OrderBookController();
	private OptimiserController optimiser = new OptimiserController(this);
	private TimeStrategyConfigView config = null;
	private IndicatorCache indicators = new IndicatorCache();
	
	private JTabbedPane tabbedPane;
	
	private CandleCollection candles = new CandleCollection();
	
	public StrategyView() {

		log.debug("building new StrategyView");
		
		setLayout(new BorderLayout());

		// create tabs for chart & order book
		tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		// add the tabs
		tabbedPane.add(optimiser.getView(), "Optimiser");
        optimiser.setStrategyClass(TimeStrategy.class);
		tabbedPane.add(orderBook.getView(), "Order Book");

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
					orderBook.update(candles, null);
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
		TimeStrategy strategy = new TimeStrategy(1, config.getParams(), indicators);
		strategy.execute(candles);

		// update the controllers
		OrderBook orders = strategy.getOrderBook();
		orderBook.update(candles, orders);
		
		// activate the order book tab
		tabbedPane.setSelectedIndex(1);
	}
}
