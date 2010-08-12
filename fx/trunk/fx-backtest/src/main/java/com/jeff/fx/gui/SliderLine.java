package com.jeff.fx.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.backtest.AppCtx;

public class SliderLine extends JXPanel {
	
	private static final long serialVersionUID = 1561678788540967715L;

	private JXLabel lblLabel;
	private JXLabel lblValue;
	private JSlider slider;
	private int step;
	
	public SliderLine(final String key, final String label, final int startValue, final int endValue, final int step) {
		
		lblLabel = new JXLabel(label);
		lblValue = new JXLabel("N/A");
		slider = new JSlider(startValue / step, endValue / step);
		slider.setValue(startValue);

		this.step = step;
		
		lblLabel.setPreferredSize(new Dimension(60, 18));
		lblValue.setPreferredSize(new Dimension(60, 18));
		slider.setPreferredSize(new Dimension(150, 18));
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				AppCtx.set(key, slider.getValue());
				lblValue.setText(String.format("%d", slider.getValue() * step));
			}
		});
		
		add(lblLabel);
		add(slider);
		add(lblValue);
	}
	
	public void setValue(int val) {
		slider.setValue(val / step);
	}

	public int getValue() {
		return slider.getValue() * step;
	}
	
	public void addChangeListener(ChangeListener listener) {
		slider.addChangeListener(listener);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(450, 200));
		frame.add(new SliderLine("test.slider", "Slider", 0, 250, 5));
		frame.setVisible(true);
	}
}

