package com.siebentag.fx.mv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.springframework.stereotype.Component;

import com.siebentag.fx.strategy.PriceList;

@Component
public class DataSummaryPanel extends JXPanel
{
	private JXTable table;
	private DataSummaryTableModel model;
	
	public DataSummaryPanel()
	{
		model = new DataSummaryTableModel();
		table = new JXTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(600, 175));
	}
	
	public void setDeltaList(TickAggregationDeltaList deltaList)
	{
		model.setDeltaList(deltaList);
	}
}

class DataSummaryTableModel extends DefaultTableModel
{
	private TickAggregationDeltaList data;
	
	public Object getValueAt(int row, int col)
	{
		PriceList pl = data==null?null:(col==1?data.getLongList():data.getShortList());
		
		switch(col)
		{
			case 0: switch(row) {
				case 0: return "Sum";
				case 1: return "Min";
				case 2: return "Max";
				case 3: return "Avg";
				case 4: return "Median";
				case 5: return "Profit";
				case 6: return "Loss";
			}

			case 1:
			case 2:
				if(pl == null) return "";
				switch(row) {
					case 0: return String.format("%.1f", pl.sum());
					case 1: return String.format("%.1f", pl.min());
					case 2: return String.format("%.1f", pl.max());
					case 3: return String.format("%.1f", pl.average());
					case 4: return String.format("%.1f", pl.median());
					case 5: return String.format("%d (%.1f%%)", pl.positive(), (double)pl.positive() / (double)pl.size() * 100.0);
					case 6: return String.format("%d (%.1f%%)", pl.negative(), (double)pl.negative() / (double)pl.size() * 100.0);
				}
			
			default: return "ERROR";
		}
	}
	
	public int getRowCount()
	{
		return 7;
	}
	
	public void setDeltaList(TickAggregationDeltaList deltaList)
	{
		this.data = deltaList;
	}
	
	public int getColumnCount()
	{
		return 3;
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