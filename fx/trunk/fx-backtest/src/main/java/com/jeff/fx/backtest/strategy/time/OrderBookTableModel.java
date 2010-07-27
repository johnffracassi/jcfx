package com.jeff.fx.backtest.strategy.time;

import javax.swing.table.DefaultTableModel;

import org.joda.time.LocalDateTime;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;

@SuppressWarnings("serial")
public class OrderBookTableModel extends DefaultTableModel {

	private OrderBook book = new OrderBook();

	public OrderBookTableModel() {
	}

	public void update(OrderBook book) {
		this.book = book;
	}

	public Object getValueAt(int row, int column) {
		
		BTOrder order = book.getClosedOrders().get(row);
		
		switch(column) {
			case 0: return row + 1;
			case 1: return order.getOpenTime();
			case 2: return order.getOfferSide();
			case 3: return order.getUnits();
			case 4: return order.getInstrument();
			case 5: return order.getOpenPrice();
			case 6: return order.getStopLoss();
			case 7: return order.getTakeProfit();
			case 8: return order.getCloseTime();
			case 9: return order.getClosePrice();
			case 10: return 0.0;
			case 11: return order.getProfit();
			default: return "XXX";
		}
	}

	public Class<?> getColumnClass(int column) {
		switch (column) {
			case 0: return Integer.class;
			case 1: return LocalDateTime.class;
			case 2: return String.class;
			case 3: return Double.class;
			case 4: return String.class;
			case 5: return Double.class;
			case 6: return Double.class;
			case 7: return Double.class;
			case 8: return LocalDateTime.class;
			case 9: return Double.class;
			case 10: return Double.class;
			case 11: return Double.class;
			default: return String.class;
		}
	}
	
	
	public int getColumnCount() {
		return 12;
	}
	
	public String getColumnName(int column) {
		switch (column) {
			case 0: return "Order#";
			case 1: return "Open";
			case 2: return "Type";
			case 3: return "Size";
			case 4: return "Symbol";
			case 5: return "Price";
			case 6: return "S/L";
			case 7: return "T/P";
			case 8: return "Close";
			case 9: return "Price";
			case 10: return "Swap";
			case 11: return "P/L";
			default: return "";
		}
	}

	public int getRowCount() {
		if(book == null) {
			return 0;
		} else {
			return book.getClosedOrders().size();
		}
	}
}


