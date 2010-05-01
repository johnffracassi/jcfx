package com.jeff.fx.datamanager;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.LocalTime;

public class TimeRange extends JPanel {
	
	private static final long serialVersionUID = 999290000L;

	private TimePicker start;
	private TimePicker end;

	public TimeRange() {
		start = new TimePicker();
		end = new TimePicker();

		init();
	}

	public LocalTime getStart() {
		return start.getTime();
	}

	public LocalTime getEnd() {
		return end.getTime();
	}

	private void init() {
		setLayout(new FlowLayout(3));

		add(new JLabel("Start Time"));
		add(start);
		add(new JLabel("End Time"));
		add(end);
	}
}
