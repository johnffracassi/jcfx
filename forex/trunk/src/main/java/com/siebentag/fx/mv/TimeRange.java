package com.siebentag.fx.mv;

import java.awt.FlowLayout;
import java.sql.Time;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeRange extends JPanel
{
	private static final long serialVersionUID = 999290000L;
	
	private TimePicker start;
	private TimePicker end;
	
	public TimeRange()
	{
		start= new TimePicker();
		end= new TimePicker();
		
		init();
	}
	
	public Time getStart()
	{
		return start.getTime();
	}
	
	public Time getEnd()
	{
		return end.getTime();
	}
	
	private void init()
	{
		setLayout(new FlowLayout(3));
		
		add(new JLabel("Start Time"));
		add(start);
		add(new JLabel("End Time"));
		add(end);
	}
}
