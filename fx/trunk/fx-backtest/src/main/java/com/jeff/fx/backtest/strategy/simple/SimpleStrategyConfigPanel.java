package com.jeff.fx.backtest.strategy.simple;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.backtest.strategy.CommonStrategyPanel;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.gui.SliderLine;
import com.siebentag.gui.VerticalFlowLayout;

public class SimpleStrategyConfigPanel extends JXPanel {
	
	private static final long serialVersionUID = 7675491170716141950L;

	public SimpleStrategyConfigPanel(final StrategyPropertyChangeListener spcl) {
		
		JXPanel pnlSS = new JXPanel();
		pnlSS.setLayout(new VerticalFlowLayout(0));
		SliderLine pnlOpenFor = new SliderLine("simpleStrategy.openFor", "Open For", 0, 100);
		SliderLine pnlCloseFor = new SliderLine("simpleStrategy.closedFor", "Closed For", 0, 100);
		pnlSS.add(pnlOpenFor);
		pnlSS.add(pnlCloseFor);
		
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				try {
					spcl.update();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		
		pnlOpenFor.addChangeListener(listener);
		pnlCloseFor.addChangeListener(listener);

		add(new CommonStrategyPanel(spcl));
		add(pnlSS);
	}
}

