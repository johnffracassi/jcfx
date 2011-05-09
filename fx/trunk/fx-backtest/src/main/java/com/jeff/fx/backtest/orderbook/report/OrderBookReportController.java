package com.jeff.fx.backtest.orderbook.report;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.orderbook.OrderSelectionListener;
import com.jeff.fx.backtest.orderbook.export.OrderBookExportManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderBookReportController {

	private OrderBookReportModel model = new OrderBookReportModel();
	private OrderBookReportView view = new OrderBookReportView();
	private List<OrderSelectionListener> listeners = new ArrayList<OrderSelectionListener>();
	
	public OrderBookReportController() {
		
		view.getTblBook().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if(ev.getClickCount() == 2 && ev.getButton() == MouseEvent.BUTTON1) {
					int orderIdx = view.getTblBook().convertRowIndexToModel(view.getTblBook().getSelectedRow());
					BTOrder order = model.getOrderBook().getClosedOrders().get(orderIdx);
					if(order != null) {
						for(OrderSelectionListener listener : listeners) {
							listener.orderSelected(order);
						}
					}
				}
			}
		});
		
		final OrderBookExportManager exportManager = (OrderBookExportManager)AppCtx.getBean("orderBookExportManager");
		view.getBtnExport().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OrderBook book = model.getOrderBook();
					exportManager.export("csv", book, new File("orderBook.csv"));
                    System.out.println("Order book exported to " + new File("orderBook.csv").getAbsolutePath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void addOrderSelectionListener(OrderSelectionListener listener) {
		listeners.add(listener);
	}
	
	public OrderBookReportView getView() {
		return view;
	}

	public void update(OrderBook book) {
		model.update(book);
		view.update(model);
	}
}
