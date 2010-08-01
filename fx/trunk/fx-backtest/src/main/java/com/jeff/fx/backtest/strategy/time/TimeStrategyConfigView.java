package com.jeff.fx.backtest.strategy.time;

import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.strategy.CommonStrategyPanel;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.common.TimeOfWeek;
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

	public Map<String,Object> getParams() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("stopLoss", getStopLoss());
		params.put("takeProfit", getTakeProfit());
		params.put("open", getOpen());
		params.put("close", getClose());
		return params;
	}
	
	public void setParams(Map<String,Object> params) {
		sldOpen.setTimeOfWeek((TimeOfWeek)params.get("open"));
		sldClose.setTimeOfWeek((TimeOfWeek)params.get("close"));
		pnlCommon.setStopLoss((Integer)params.get("stopLoss"));
		pnlCommon.setTakeProfit((Integer)params.get("takeProfit"));
	}
	
	public TimeOfWeek getOpen() {
		return sldOpen.getTimeOfWeek();
	}
	
	public TimeOfWeek getClose() {
		return sldClose.getTimeOfWeek();
	}
	
	public int getStopLoss() {
		return pnlCommon.getStopLoss();
	}
	
	public int getTakeProfit() {
		return pnlCommon.getTakeProfit();
	}
}

