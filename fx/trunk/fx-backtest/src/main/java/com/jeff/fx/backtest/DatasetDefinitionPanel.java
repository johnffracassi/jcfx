package com.jeff.fx.backtest;

import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.gui.field.PEnumComboBox;
import com.siebentag.gui.VerticalFlowLayout;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.joda.time.LocalDate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	}

    public FXDataRequest getRequest()
    {
        FXDataSource dataSource = FXDataSource.valueOf(AppCtx.getPersistent(prefix + ".dataSource"));
        Instrument instrument = Instrument.valueOf(AppCtx.getPersistent(prefix + ".instrument"));
        Period period = Period.valueOf(AppCtx.getPersistent(prefix + ".period"));
        FXDataRequest request = new FXDataRequest(dataSource, instrument, AppCtx.getPersistentDate(prefix + ".startDate"), AppCtx.getPersistentDate(prefix + ".endDate"), period);
        return request;
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