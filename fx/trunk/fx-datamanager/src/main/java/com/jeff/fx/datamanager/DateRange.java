package com.jeff.fx.datamanager;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.jdesktop.swingx.JXDatePicker;

public class DateRange extends JPanel
{
	private static final long serialVersionUID = 99990000L;
	
	private JXDatePicker startDate;
	private JXDatePicker endDate;
	private List<Date> fastDates;
	private JPopupMenu popup;
	
	public DateRange()
	{
		fastDates = new ArrayList<Date>();
		startDate = new JXDatePicker();
		endDate = new JXDatePicker();
		popup = new JPopupMenu("Popup");
		
		init();
	}
	
	public Date getStart()
	{
		return startDate.getDate();
	}
	
	public Date getEnd()
	{
		return endDate.getDate();
	}
	
	public void clearFastDates()
	{
		fastDates.clear();
	}
	
	public void addFastDate(Date date)
	{
		fastDates.add(date);
		popup.add(new JMenuItem(date.toString()));
	}
	
	private void init()
	{
		setLayout(new FlowLayout(3));
		
		add(new JLabel("Start Date"));
		add(startDate);
		add(new JLabel("End Date"));
		add(endDate);
		
		setComponentPopupMenu(popup);
	}
}
