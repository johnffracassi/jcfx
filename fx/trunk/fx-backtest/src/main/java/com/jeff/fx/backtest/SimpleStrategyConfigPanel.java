package com.jeff.fx.backtest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class SimpleStrategyConfigPanel extends JXPanel {
	
	public SimpleStrategyConfigPanel(final SimpleStrategyChartPanel chartPanel) {
		
		setLayout(new VerticalFlowLayout(1));
		setBackground(Color.CYAN);
		
		final JSlider sldOpenFor = new JSlider(5, 200);
		sldOpenFor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				AppCtx.set("simpleStrategy.openFor", sldOpenFor.getValue());
				try {
					chartPanel.update();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		final JSlider sldClosedFor = new JSlider(5, 200);
		sldClosedFor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				AppCtx.set("simpleStrategy.closedFor", sldClosedFor.getValue());
				try {
					chartPanel.update();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		add(createLine("Open For", 130, sldOpenFor));
		add(createLine("Closed For", 130, sldClosedFor));
	}
	
	private JXPanel createLine(String label, int width, Component component) {
		
		JXPanel pnl = new JXPanel();
		int height = (int)component.getPreferredSize().getHeight();
		
		JXLabel lbl = new JXLabel(label);
		lbl.setPreferredSize(new Dimension(80, height));
		pnl.add(lbl);
		
		component.setPreferredSize(new Dimension(130, height));
		pnl.add(component);
		
		pnl.setPreferredSize(new Dimension(220, height + 2));
		
		return pnl;
	}
}