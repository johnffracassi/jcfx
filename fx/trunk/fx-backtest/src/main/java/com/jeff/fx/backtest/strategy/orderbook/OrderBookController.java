package com.jeff.fx.backtest.strategy.orderbook;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;


public class OrderBookController {

	private OrderBookModel model = new OrderBookModel();
	private OrderBookView view = new OrderBookView();
	private List<OrderSelectionListener> listeners = new ArrayList<OrderSelectionListener>();
	
	public OrderBookController() {
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
	
	public OrderBookView getView() {
		return view;
	}

	public void update(OrderBook book) {
		model.update(book);
		view.update(model);
	}
}
