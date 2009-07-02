package com.siebentag.fx.tbv;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public class TimeSlider extends JPanel
{
	private JSlider slider;
	private int period;
	
	public TimeSlider(final String label)
	{
		final JLabel lblValue = new JLabel("10:00");
		
		slider = new JSlider(0, 1439, 600);		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				lblValue.setText(DateTimeFormat.shortTime().print(getTime()));
			}
		});
		
		add(new JLabel(label));
		add(slider);
		add(lblValue);
	}
	
	public void addChangeListener(ChangeListener listener)
	{
		slider.addChangeListener(listener);
	}
	
	public void setPeriod(int period)
	{
		slider.setMinimum(0);
		slider.setMaximum((1440 / period) - 1);
		slider.setValue(slider.getValue() * this.period / period);
		this.period = period;
	}
	
	public LocalTime getTime()
	{
		int num = slider.getValue() * period;
		return new LocalTime(num / 60, num % 60);
	}
}
