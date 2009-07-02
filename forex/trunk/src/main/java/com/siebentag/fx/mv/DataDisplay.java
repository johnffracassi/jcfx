package com.siebentag.fx.mv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.springframework.stereotype.Component;

@Component
public class DataDisplay extends JXPanel
{
	private JXTable table;
	private TickAggregationTableModel model;
	
	public DataDisplay()
	{
		model = new TickAggregationTableModel();
		table = new JXTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(600, 450));
		
		table.getColumn(1).setCellRenderer(new DoubleRenderer(4, false));
		table.getColumn(2).setCellRenderer(new DoubleRenderer(4, false));
		table.getColumn(3).setCellRenderer(new DoubleRenderer(0, false));
		table.getColumn(4).setCellRenderer(new DoubleRenderer(1, true));
		table.getColumn(5).setCellRenderer(new DoubleRenderer(4, false));
		table.getColumn(6).setCellRenderer(new DoubleRenderer(4, false));
		table.getColumn(7).setCellRenderer(new DoubleRenderer(0, false));
		table.getColumn(8).setCellRenderer(new DoubleRenderer(1, true));
	}
	
	public void setDeltaList(List<TickAggregationRow> deltaList)
	{
		model.setDeltaList(deltaList);
		
		System.out.println("======================================================");
		for(TickAggregationRow row : deltaList)
		{
			System.out.println(row.getStartDate() + "\t" + row.getDrawdownLong() + "\t" + row.getProfitLong() + "\t" + row.getDrawdownShort() + "\t" + row.getProfitShort());
		}
	}
}

class TickAggregationTableModel extends DefaultTableModel
{
	private List<TickAggregationRow> deltaList;

	public void setDeltaList(List<TickAggregationRow> deltaList)
	{
		this.deltaList = deltaList;
		fireTableDataChanged();
	}
	
	public Object getValueAt(int row, int col)
	{
		TickAggregationRow delta = (TickAggregationRow)deltaList.get(row);
		
		switch(col)
		{
			case 0: return delta.getStartDate();
			
			case 1: return delta.getOpenBuy();
			case 2: return delta.getCloseSell();
			case 3: return delta.getDrawdownLong();
			case 4: return delta.getProfitLong();
			
			case 5: return delta.getOpenSell();
			case 6: return delta.getCloseBuy();
			case 7: return delta.getDrawdownShort();
			case 8: return delta.getProfitShort();
			
			default: return "ERROR";
		}
	}
	
	public Class<?> getColumnClass(int col)
	{
		switch(col)
		{
			case 0: return Date.class;
			case 1: return Double.class;
			case 2: return Double.class;
			case 3: return Double.class;
			case 4: return Double.class;
			case 5: return Double.class;
			case 6: return Double.class;
			case 7: return Double.class;
			case 8: return Double.class;
			default: return String.class;
		}
	}
	
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}
	
	public int getRowCount()
	{
		if(deltaList == null)
		{
			return 0;
		}
		
		return deltaList.size();
	}
	
	public int getColumnCount()
	{
		return 9;
	}
	
	public String getColumnName(int col)
	{
		switch(col)
		{
			case 0: return "Start";
			case 1: return "OpenBuy";
			case 2: return "CloseSell";
			case 3: return "DD Long";
			case 4: return "PL Long";
			case 5: return "OpenSell";
			case 6: return "CloseBuy";
			case 7: return "DD Short";
			case 8: return "PL Short";
			default: return "ERR";
		}
	}
}

