package com.siebentag.fx.mv;

import javax.swing.table.DefaultTableModel;

public class DataTableModel extends DefaultTableModel
{
	public int getColumnCount()
	{
		return 5;
	}
	
	public String getColumnName(int arg0)
	{
		switch(arg0)
		{
			case 0: return "Date";
			case 1: return "O";
			case 2: return "H";
			case 3: return "L";
			case 4: return "C";
			default: return "*ERROR*";
		}
	}
}
