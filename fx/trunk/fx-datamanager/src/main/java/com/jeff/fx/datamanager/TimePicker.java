package com.jeff.fx.datamanager;

import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.jdesktop.swingx.JXPanel;
import org.joda.time.LocalTime;

public class TimePicker extends JXPanel
{
	private static final long serialVersionUID = -8584667524833960230L;

	private JComboBox cboHour;
	private JComboBox cboMinute;
	
	private int gap = 5;
	
	public TimePicker()
	{
		setLayout(new FlowLayout(3));
		
		cboHour = new JComboBox();
		cboMinute = new JComboBox();

		reset();
		
		add(cboHour);
		add(cboMinute);
	}

	private void reset()
	{
		DefaultComboBoxModel modelHour = new DefaultComboBoxModel();
		for(int hr = 0; hr < 24; hr++)
		{
			modelHour.addElement(hr);
		}
		cboHour.setModel(modelHour);
		
		DefaultComboBoxModel modelMinute = new DefaultComboBoxModel();
		for(int i=0; i<60; i+= gap)
		{
			modelMinute.addElement(i);
		}
		cboMinute.setModel(modelMinute);
	}
	
	public LocalTime getTime()
	{
		int hr = (Integer)cboHour.getSelectedItem();
		int min = (Integer)cboMinute.getSelectedItem();
		
		return new LocalTime(hr, min, 0);
	}
}
