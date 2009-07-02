package com.siebentag.fx.tbv;

import javax.swing.table.DefaultTableModel;

import org.joda.time.LocalDateTime;

import com.siebentag.fx.backtest.BalanceSheet;
import com.siebentag.fx.backtest.Order;
import com.siebentag.fx.backtest.OrderCloseType;
import com.siebentag.fx.backtest.OrderSide;

public class OrdersTableModel extends DefaultTableModel
{
	private BalanceSheet data;

	public void setBalanceSheet(BalanceSheet balanceSheet)
	{
		data = balanceSheet;
		fireTableDataChanged();
	}
	
	public Object getValueAt(int row, int col)
	{
		Order order = data.getClosedOrders().get(row);
		
		switch(col)
		{
			case 0: return order.getOpen().getLocalDateTime();
			case 1: return order.getSide();
			case 2: return order.getOpenPrice();
			case 3: return order.getClose().getLocalDateTime();
			case 4: return order.getClosePrice();
			case 5: return order.getPipProfitLoss();
			case 6: return order.getCloseType();
			
			default: return "ERROR";
		}
	}
	
	public Class<?> getColumnClass(int col)
	{
		switch(col)
		{
			case 0: return LocalDateTime.class;
			case 1: return OrderSide.class;
			case 2: return Double.class;
			case 3: return LocalDateTime.class;
			case 4: return Double.class;
			case 5: return Double.class;
			case 6: return OrderCloseType.class;
			default: return String.class;
		}
	}
	
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}
	
	public int getRowCount()
	{
		if(data == null)
		{
			return 0;
		}
		
		return data.getClosedOrders().size();
	}
	
	public int getColumnCount()
	{
		return 7;
	}
	
	public String getColumnName(int col)
	{
		switch(col)
		{
			case 0: return "Open Time";
			case 1: return "Side";
			case 2: return "Open";
			case 3: return "Close Time";
			case 4: return "Close";
			case 5: return "Pip P/L";
			case 6: return "Close Type";
			default: return "ERR";
		}
	}
}
