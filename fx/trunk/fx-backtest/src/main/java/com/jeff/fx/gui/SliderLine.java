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
	
	public SliderLine(final String key, final String label, final int startValue, final int endValue) {
		
		lblLabel = new JXLabel(label);
		lblValue = new JXLabel("N/A");
		slider = new JSlider(startValue, endValue);
		slider.setValue(startValue);

		lblLabel.setPreferredSize(new Dimension(100, 20));
		lblValue.setPreferredSize(new Dimension(50, 20));
		slider.setPreferredSize(new Dimension(250, 25));
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				AppCtx.set(key, slider.getValue());
				lblValue.setText(String.format("%d", slider.getValue()));
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(450, 200));
		frame.add(new SliderLine("test.slider", "Slider", 0, 200));
		frame.setVisible(true);
	}
}

