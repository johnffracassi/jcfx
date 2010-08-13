package com.jeff.fx.backtest.strategy.time;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.backtest.strategy.CommonStrategyPanel;
import com.jeff.fx.backtest.strategy.StrategyPropertyChangeListener;
import com.jeff.fx.common.OfferSide;
import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.gui.GUIUtil;
import com.jeff.fx.gui.TimeOfWeekSliderLine;
import com.siebentag.gui.VerticalFlowLayout;

public class TimeStrategyConfigView extends JXPanel {
	
	private static final long serialVersionUID = 7675491170754141950L;

	private CommonStrategyPanel pnlCommon;
	private TimeOfWeekSliderLine sldOpen;
	private TimeOfWeekSliderLine sldClose;
	private JComboBox cboOfferSide;
	
	public TimeStrategyConfigView(final StrategyPropertyChangeListener spcl) {
		
		JXPanel pnlConfig = new JXPanel();
		pnlConfig.setLayout(new VerticalFlowLayout(0));
		sldOpen = new TimeOfWeekSliderLine("timeStrategy.openAt", "Open", 0, 100);
		sldClose = new TimeOfWeekSliderLine("timeStrategy.closeAt", "Close", 0, 100);
		pnlConfig.add(sldOpen);
		pnlConfig.add(sldClose);
		
		JXPanel pnlOfferSide = new JXPanel();
		cboOfferSide = new JComboBox(OfferSide.values());
		cboOfferSide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					spcl.update();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		pnlOfferSide.add(new JXLabel("Offer Side:"));
		pnlOfferSide.add(cboOfferSide);
		pnlConfig.add(pnlOfferSide);
		
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
		
		setLayout(new BorderLayout());
		JXPanel container = new JXPanel(new BorderLayout());
		container.setLayout(new GridLayout(1, 2));
		container.add(pnlCommon);
		container.add(pnlConfig);
		add(GUIUtil.frame("Strategy Parameters", container), BorderLayout.CENTER);
	}

	public Map<String,Object> getParams() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("stopLoss", getStopLoss());
		params.put("takeProfit", getTakeProfit());
		params.put("open", getOpen());
		params.put("close", getClose());
		params.put("offerSide", getOfferSide());
		return params;
	}
	
	public void setParams(Map<String,Object> params) {
		sldOpen.setTimeOfWeek((TimeOfWeek)params.get("open"));
		sldClose.setTimeOfWeek((TimeOfWeek)params.get("close"));
		pnlCommon.setStopLoss((Integer)params.get("stopLoss"));
		pnlCommon.setTakeProfit((Integer)params.get("takeProfit"));
		cboOfferSide.setSelectedItem((OfferSide)params.get("offerSide"));
	}
	
	public OfferSide getOfferSide() {
		return (OfferSide)cboOfferSide.getSelectedItem();
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

