package com.siebentag.fx.source.dukascopy;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StatsPanel extends JPanel
{
	private static DefaultTableModel model = new DefaultTableModel(3,2);
	
	private static int records = 0;
	private static long bytesCompressed = 0;
	private static long bytesUncompressed = 0;
	private static int executed = 0;
	private static int completed = 0;
	private static int failed = 0;
	
	public static void addRecords(int x)
	{
		records += x;
		model.setValueAt(records, 2, 1);
	}
	
	public static void addData(long x, boolean compressed)
	{
		if(compressed)
			bytesCompressed += x;
		else
			bytesUncompressed += x;
		
		model.setValueAt(String.format("%.2fmb / %.2fmb", (double)bytesCompressed/1024.0/1024.0, (double)bytesUncompressed/1024.0/1024.0), 1, 1);
	}
	
	public static void addExecuted()
	{
		executed ++;
		updateJobCount();
	}
	
	public static void addCompleted()
	{
		completed ++;
		updateJobCount();
	}
	
	public static void addFailed()
	{
		failed ++;
		updateJobCount();
	}
	
	private static void updateJobCount()
	{
		model.setValueAt(String.format("%d / %d / %d", executed, completed, failed), 0, 1);
	}
	
	public StatsPanel()
	{
		setLayout(new BorderLayout());
		add(new JTable(model), BorderLayout.CENTER);
		
		model.setValueAt("Jobs Exec/Comp/Fail", 0, 0);
		model.setValueAt("Data Comp/Uncomp", 1, 0);
		model.setValueAt("Records Inserted", 2, 0);
	}
}
