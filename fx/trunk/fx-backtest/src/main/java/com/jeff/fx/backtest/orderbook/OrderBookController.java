package com.jeff.fx.backtest.orderbook;

import javax.swing.ImageIcon;

import com.jeff.fx.backtest.dataviewer.CandleDataController;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.orderbook.balancechart.BalanceChartController;
import com.jeff.fx.backtest.orderbook.report.OrderBookReportController;
import com.jeff.fx.common.CandleCollection;

public class OrderBookController implements OrderSelectionListener {

	private OrderBookView view;
	private OrderBookReportController report;
	private BalanceChartController balance;
	private CandleDataController dataViewer;
	
	public OrderBookController() {

		// build all the required controllers
		report = new OrderBookReportController();
		balance = new BalanceChartController();
		dataViewer = new CandleDataController();

		// create tabbed panels in view
		view = new OrderBookView();
		view.getTabbedPane().addTab("Balance Chart", new ImageIcon(getClass().getResource("/images/chart_curve.png")), balance.getView());
		view.getTabbedPane().addTab("Orders", new ImageIcon(getClass().getResource("/images/book_open.png")), report.getView());
		view.getTabbedPane().addTab("Candles", new ImageIcon(getClass().getResource("/images/application_view_columns.png")), dataViewer.getView());
		
		// wire the listeners together
		report.addOrderSelectionListener(dataViewer);
		report.addOrderSelectionListener(this);
	}
	
	public void update(CandleCollection candles, OrderBook book) {
		
		if(book != null) {
			report.update(book);
			balance.update(book);
		}
		
		if(candles != null) {
			dataViewer.update(candles);
		}
	}

	public OrderBookView getView() {
		return view;
	}

	public void orderSelected(BTOrder order) {
		view.getTabbedPane().setSelectedIndex(2);
	}	
}
