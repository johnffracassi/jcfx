package com.siebentag.fx.tbv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.joda.time.LocalDate;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.mv.DoubleRenderer;

public class TimeValueTable extends JXPanel
{
	private JXTable table;
	private TimeValueTableModel model;
	
	public TimeValueTable()
	{
		model = new TimeValueTableModel();
		table = new JXTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(600, 250));
		
		table.getColumn(0).setCellRenderer(new DateTimeRenderer());
		table.getColumn(1).setCellRenderer(new DoubleRenderer(0, false));
		table.getColumn(2).setCellRenderer(new DoubleRenderer(3, false));
		table.getColumn(3).setCellRenderer(new DoubleRenderer(1, false));
		table.getColumn(4).setCellRenderer(new DoubleRenderer(1, false));
		table.getColumn(5).setCellRenderer(new DoubleRenderer(3, false));
		table.getColumn(6).setCellRenderer(new DoubleRenderer(1, false));
		table.getColumn(7).setCellRenderer(new DoubleRenderer(1, false));
		table.getColumn(8).setCellRenderer(new DoubleRenderer(3, false));
	}
	
	public void setCandles(LocalDate startDate, List<CandleStickDataPoint> candles)
	{
		model.setCandleList(startDate, candles);
	}
}