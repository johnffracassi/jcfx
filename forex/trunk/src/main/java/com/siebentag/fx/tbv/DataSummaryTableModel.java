package com.siebentag.fx.tbv;

import javax.swing.table.DefaultTableModel;

import com.siebentag.fx.backtest.BalanceSheet;

public class DataSummaryTableModel extends DefaultTableModel
{
	private BalanceSheet data;
	
	public Object getValueAt(int row, int col)
	{
		switch(col)
		{
			case 0: switch(row) {
				case 0: return "Balance";
				case 1: return "Drawdown";
				case 2: return "Profit/Drawdown";
				case 3: return "Wins";
				case 4: return "Losses";
				case 5: return "Close / Limit / Stop";
			}

			case 1:
				if(data == null || data.orderCount() == 0) return "";
				
				switch(row) {
					case 0: return String.format("%.1f", data.getBalance());
					case 1: return String.format("%.1f", data.calculateMaximumDrawdown());
					case 2: return String.format("%.1f", (data.getBalance() - 10000.0) / data.calculateMaximumDrawdown());
					case 3: return String.format("%d (%.1f%%)", data.wins(), (double)data.wins() / (double)data.orderCount() * 100.0);
					case 4: return String.format("%d (%.1f%%)", data.losses(), (double)data.losses() / (double)data.orderCount() * 100.0);
					case 5: int[] closeTypeCount = data.getCloseTypeCount();
							return String.format("%d / %d / %d", closeTypeCount[0], closeTypeCount[1], closeTypeCount[2]);
				}
			
			default: return "ERROR";
		}
	}
	
	public int getRowCount()
	{
		return 6;
	}
	
	public void setBalanceSheet(BalanceSheet balanceSheet)
	{
		this.data = balanceSheet;
		fireTableDataChanged();
	}
	
	public int getColumnCount()
	{
		return 2;
	}
	
	public Class<?> getColumnClass(int col)
	{
		return String.class;
	}
	
	public String getColumnName(int col)
	{
		switch(col)
		{
			case 0: return "Category";
			case 1: return "Long";
			case 2: return "Short";
			default: return "ERROR";
		}
	}
}
