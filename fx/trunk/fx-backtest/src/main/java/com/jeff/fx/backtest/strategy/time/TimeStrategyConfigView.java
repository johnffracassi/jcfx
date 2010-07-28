package com.jeff.fx.backtest.strategy.time;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;
import org.joda.time.LocalTime;

import com.jeff.fx.backtest.strategy.CommonStrategyPanel;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.gui.TimeOfWeekSliderLine;
import com.siebentag.gui.VerticalFlowLayout;

public class TimeStrategyConfigView extends JXPanel {
	
	private static final long serialVersionUID = 7675491170754141950L;

	private CommonStrategyPanel pnlCommon;
	private TimeOfWeekSliderLine sldOpen;
	private TimeOfWeekSliderLine sldClose; 
	
	public TimeStrategyConfigView(final StrategyPropertyChangeListener spcl) {
		
		JXPanel pnlConfig = new JXPanel();
		pnlConfig.setLayout(new VerticalFlowLayout(0));
		sldOpen = new TimeOfWeekSliderLine("timeStrategy.openAt", "Open", 0, 100);
		sldClose = new TimeOfWeekSliderLine("timeStrategy.closeAt", "Close", 0, 100);
		pnlConfig.add(sldOpen);
		pnlConfig.add(sldClose);
		
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				try {
					spcl.update();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		
		sldOpen.addChangeListener(listener);
		sldClose.addChangeListener(listener);

		pnlCommon = new CommonStrategyPanel(spcl);
				
		add(pnlCommon);
		add(pnlConfig);
	}

	public LocalTime getOpenTime() {
		return sldOpen.getTime();
	}
	
	public int getOpenDay() {
		return sldOpen.getDayOfWeek();
	}
	
	public LocalTime getCloseTime() {
		return sldClose.getTime();
	}
	
	public int getCloseDay() {
		return sldClose.getDayOfWeek();
	}
	
	public double getStopLoss() {
		return pnlCommon.getStopLoss();
	}
	
	public double getTakeProfit() {
		return pnlCommon.getTakeProfit();
	}
}

