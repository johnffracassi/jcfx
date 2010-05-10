package com.jeff.fx.backtest;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		setLayout(new VerticalFlowLayout(0));
		
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

		final String name = prefix + "." + key;
		final LocalDate defaultDate = AppCtx.retrieveDate(name);
		final JXDatePicker date = new JXDatePicker(defaultDate.toDateTimeAtStartOfDay().toDate());
		
		date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.update(name, new LocalDate(date.getDate()));
			}
		});
		
		return (createLine(label, 130, date));
	}
	
	private JXPanel createLine(String label, int width, Component component) {
		
		JXPanel pnl = new JXPanel();
		int height = (int)component.getPreferredSize().getHeight();
		
		JXLabel lbl = new JXLabel(label);
		lbl.setPreferredSize(new Dimension(80, height));
		pnl.add(lbl);
		
		component.setPreferredSize(new Dimension(130, height));
		pnl.add(component);
		
		pnl.setPreferredSize(new Dimension(220, height + 8));
		
		return pnl;
	}
}