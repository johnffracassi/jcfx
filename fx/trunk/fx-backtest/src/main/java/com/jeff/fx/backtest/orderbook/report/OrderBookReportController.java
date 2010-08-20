package com.jeff.fx.backtest.orderbook.report;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.orderbook.OrderSelectionListener;

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
