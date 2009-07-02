package com.siebentag.fx.tbv;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class DateSlider extends JPanel
{
	private JSlider slider;
	private LocalDate startDate;
	
	public DateSlider(final String label)
	{
		final JLabel lblValue = new JLabel(label);
		
		slider = new JSlider(0, 10);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblValue.setText(getDate().toString());
			}
		});
		
		add(new JLabel(label));
		add(slider);
		add(lblValue);
	}
	
	public void setDateRange(LocalDate start, LocalDate end)
	{
		this.startDate = start;
		slider.setMinimum(0);
		slider.setMaximum(Days.daysBetween(start, end).getDays());
	}
	
	public void addChangeListener(ChangeListener listener)
	{
		slider.addChangeListener(listener);
	}
	
	public LocalDate getDate()
	{
		return startDate.plusDays(slider.getValue());
	}
}

