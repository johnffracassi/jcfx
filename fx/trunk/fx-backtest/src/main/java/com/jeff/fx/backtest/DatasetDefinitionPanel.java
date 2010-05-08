package com.jeff.fx.backtest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class DatasetDefinitionPanel extends JXPanel {
	
	private String prefix;
	
	public DatasetDefinitionPanel(String prefix) {
		
		this.prefix = prefix;
		
		setLayout(new VerticalFlowLayout(1));
		setBackground(Color.BLUE);
		
		add(createLine("Data Source", 130, new PComboBox(FXDataSource.values(), prefix + ".dataSource") {
			public Object resolve(String str) { return (str == null) ? (FXDataSource.values()[0]) : (FXDataSource.valueOf(str)); }
		}));
		
		add(createLine("Instrument", 130, new PComboBox(Instrument.values(), prefix + ".instrument") {
			public Object resolve(String str) { return (str == null) ? (Instrument.values()[0]) : (Instrument.valueOf(str)); }
		}));
		
		add(createLine("Period", 130, new PComboBox(Period.values(), prefix + ".period") {
			public Object resolve(String str) { return (str == null) ? (Period.values()[0]) : (Period.valueOf(str)); }
		}));

		add(createDateLine("Start Date", "startDate"));
		add(createDateLine("End Date", "endDate"));
		
		JXPanel pnlActions = new JXPanel();
		JXButton btnNewChart = new JXButton(new NewCandleChartAction());
		pnlActions.add(btnNewChart);
		add(pnlActions);
	}
	
	private JXPanel createDateLine(final String label, final String key) {

		final JXDatePicker startDate = new JXDatePicker(new Date(System.currentTimeMillis() - (3*24*60*60*1000)));
		startDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.set(prefix + "." + key, new LocalDate(startDate.getDate()));
			}
		});
		
		return (createLine(label, 130, startDate));
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