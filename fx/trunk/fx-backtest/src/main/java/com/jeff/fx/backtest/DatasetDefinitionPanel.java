package com.jeff.fx.backtest;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.joda.time.LocalDate;

import com.jeff.fx.backtest.chart.NewCandleChartAction;
import com.jeff.fx.backtest.chart.NewPriceChartAction;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.gui.PEnumComboBox;
import com.siebentag.gui.VerticalFlowLayout;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class DatasetDefinitionPanel extends JPanel {
	
	private String prefix;
	
	public DatasetDefinitionPanel(String prefix) {
		
		this.prefix = prefix;
		
		setLayout(new VerticalFlowLayout(0));
		
		add(createLine("Data Source", 130, new PEnumComboBox<FXDataSource>(prefix + ".dataSource", FXDataSource.class)));
		add(createLine("Instrument", 130, new PEnumComboBox<Instrument>(prefix + ".instrument", Instrument.class)));
		add(createLine("Period", 130, new PEnumComboBox<Period>(prefix + ".period", Period.class)));
		add(createDateLine("Start Date", "startDate"));
		add(createDateLine("End Date", "endDate"));
		
		JXPanel pnlActions = new JXPanel();
		JXButton button = new JXButton(new NewPriceChartAction());
		button.setIcon(new ImageIcon(DatasetDefinitionPanel.class.getResource("/images/chart_curve_add.png")));
		pnlActions.add(button);
		JXButton button_1 = new JXButton(new NewCandleChartAction());
		button_1.setIcon(new ImageIcon(DatasetDefinitionPanel.class.getResource("/images/chart_line_add.png")));
		pnlActions.add(button_1);
		add(pnlActions);
	}
	
	private JXPanel createDateLine(final String label, final String key) {

		final String name = prefix + "." + key;
		final LocalDate defaultDate = AppCtx.getPersistentDate(name);
		final JXDatePicker date = new JXDatePicker(defaultDate.toDateTimeAtStartOfDay().toDate());
		
		date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.setPersistent(name, new LocalDate(date.getDate()));
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