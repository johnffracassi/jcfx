package com.jeff.fx.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.common.TimeOfWeek;
import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class TimeOfWeekSliderLine extends JXPanel {
	
	private int multiplier = 15;
	private TimeOfWeek time = new TimeOfWeek();
	private final JSlider slider;
	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	
	public TimeOfWeekSliderLine(final String key, final String label, final int startValue, final int endValue) {
		
		int minutes = 1440 * 7;
		int ticks = minutes / multiplier - 1;
		
		final JXLabel lblLabel = new JXLabel(label);
		final JXLabel lblValue = new JXLabel(String.valueOf(new TimeOfWeek()));
		slider = new JSlider(1320/multiplier, 8460/multiplier);

		lblLabel.setPreferredSize(new Dimension(75, 20));
		lblValue.setPreferredSize(new Dimension(75, 20));
		slider.setPreferredSize(new Dimension(150, 25));
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				
				time = new TimeOfWeek(slider.getValue() * 15);
				lblValue.setText(String.valueOf(time));

				// set the value in the registry
				AppCtx.set(key + ".dayOfWeek", time.getDayOfWeek());
				AppCtx.set(key + ".time", time.getTime());
				
				for(ChangeListener listener : listeners) {
					listener.stateChanged(ev);
				}
			}
		});

		slider.setValue(0);

		add(lblLabel);
		add(slider);
		add(lblValue);
	}
	
	public void setTimeOfWeek(TimeOfWeek time) {
		this.time = time;
		slider.setValue(time.getMinuteOfWeek() / multiplier);
	}
	
	public TimeOfWeek getTimeOfWeek() {
		return time;
	}
	
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
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

