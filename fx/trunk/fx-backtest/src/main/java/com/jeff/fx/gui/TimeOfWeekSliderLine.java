package com.jeff.fx.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;

import com.jeff.fx.backtest.AppCtx;
import com.siebentag.gui.VerticalFlowLayout;

public class TimeOfWeekSliderLine extends JXPanel {
	
	private static final long serialVersionUID = 1561678788540967715L;

	private int multiplier = 15;
	private JXLabel lblLabel;
	private JXLabel lblValue;
	private JSlider slider;
	
	public TimeOfWeekSliderLine(final String key, final String label, final int startValue, final int endValue) {
		
		final LocalDateTime firstTime = new LocalDateTime(2010, 5,  9, 22, 00, 00);
		final LocalDateTime lastTime = new LocalDateTime(2010,  5, 14, 23, 00, 00);
		int minutes = Minutes.minutesBetween(firstTime, lastTime).getMinutes();
		int ticks = minutes / multiplier;
		
		lblLabel = new JXLabel(label);
		slider = new JSlider(0, ticks);
		lblValue = new JXLabel("0");

		lblLabel.setPreferredSize(new Dimension(100, 20));
		slider.setPreferredSize(new Dimension(200, 25));
		lblValue.setPreferredSize(new Dimension(100, 20));
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				LocalDateTime dt = firstTime.plusMinutes(slider.getValue() * multiplier);
				lblValue.setText(DateTimeFormat.forPattern("EE HH:mm").print(dt));

				// set the value in the registry
				int dayOfWeek = dt.getDayOfWeek();
				LocalTime time = dt.toLocalTime();
				AppCtx.set(key + ".dayOfWeek", dayOfWeek);
				AppCtx.set(key + ".time", time);
			}
		});
		
		add(lblLabel);
		add(slider);
		add(lblValue);
	}
	
	public void addChangeListener(ChangeListener listener) {
		slider.addChangeListener(listener);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new VerticalFlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(450, 200));
		frame.add(new TimeOfWeekSliderLine("test.slider1", "Slider", 0, 200));
		frame.add(new TimeOfWeekSliderLine("test.slider2", "Slider", 0, 200));
		frame.setVisible(true);
	}
}
