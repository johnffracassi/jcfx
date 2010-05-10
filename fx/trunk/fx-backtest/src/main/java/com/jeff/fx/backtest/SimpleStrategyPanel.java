package com.jeff.fx.backtest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartPanel;

import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class SimpleStrategyPanel extends JXPanel {
	
	private ChartPanel chartPanel;
	
	public SimpleStrategyPanel() {
		
		setLayout(new VerticalFlowLayout(1));
		setBackground(Color.CYAN);
		
		JSlider sldOpenFor = new JSlider(5, 200);
		sldOpenFor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
			}
		});
		
		JSlider sldClosedFor = new JSlider(5, 200);
		sldClosedFor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
			}
		});
		
		add(createLine("Open For", 130, sldOpenFor));
		add(createLine("Closed For", 130, sldClosedFor));
	
		JXPanel pnlActions = new JXPanel();
		JXButton btnNewChart = new JXButton(new NewCandleChartAction());
		pnlActions.add(btnNewChart);
		add(pnlActions);
	}
	
	private JXPanel createLine(String label, int width, Component component) {
		
		JXPanel pnl = new JXPanel();
		
		JXLabel lbl = new JXLabel(label);
		lbl.setPreferredSize(new Dimension(80, (int)lbl.getPreferredSize().getHeight()));
		pnl.add(lbl);
		
		component.setPreferredSize(new Dimension(130, (int)component.getPreferredSize().getHeight()));
		pnl.add(component);
		
		pnl.setPreferredSize(new Dimension(220, (int)component.getPreferredSize().getHeight()));
		
		return pnl;
	}
}