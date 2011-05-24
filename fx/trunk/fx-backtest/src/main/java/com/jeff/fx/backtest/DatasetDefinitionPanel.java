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

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@org.springframework.stereotype.Component
public class DatasetDefinitionPanel extends JPanel {
	
	private String prefix;
    private PEnumComboBox<FXDataSource> dataSource;
    private PEnumComboBox<Instrument> instrument;
    private PEnumComboBox<Period> period;
    private JXDatePicker startDate;
    private JXDatePicker endDate;

    public DatasetDefinitionPanel()
    {
        this.prefix = "ddp";
    }

	public DatasetDefinitionPanel(String prefix) {
		
		this.prefix = prefix;
    }

    @PostConstruct
    private void init()
    {
		setLayout(new VerticalFlowLayout(0));

		dataSource = new PEnumComboBox<FXDataSource>(prefix + ".dataSource", FXDataSource.class);
        instrument = new PEnumComboBox<Instrument>(prefix + ".instrument", Instrument.class);
        period = new PEnumComboBox<Period>(prefix + ".period", Period.class);
        startDate = createDatePicker("startDate");
        endDate = createDatePicker("endDate");

        add(createLine("Data Source", 130, dataSource));
        add(createLine("Instrument", 130, instrument));
        add(createLine("Period", 130, period));
        add(createLine("Start Date", 130, startDate));
        add(createLine("End Date", 130, endDate));
	}

    public FXDataRequest getRequest()
    {
        FXDataSource dataSource = (FXDataSource)this.dataSource.getSelectedItem();
        Instrument instrument = (Instrument)this.instrument.getSelectedItem();
        Period period = (Period)this.period.getSelectedItem();
        LocalDate start = new LocalDate(startDate.getDate());
        LocalDate end = new LocalDate(endDate.getDate());
        FXDataRequest request = new FXDataRequest(dataSource, instrument, start, end, period);
        return request;
    }

	private JXDatePicker createDatePicker(final String key) {

		final String name = prefix + "." + key;
		final LocalDate defaultDate = AppCtx.getPersistentDate(name);
		final JXDatePicker date = new JXDatePicker(defaultDate.toDateTimeAtStartOfDay().toDate());
		
		date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.setPersistent(name, new LocalDate(date.getDate()));
			}
		});
		
		return date;
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

    public PEnumComboBox<FXDataSource> getDataSource()
    {
        return dataSource;
    }

    public JXDatePicker getEndDate()
    {
        return endDate;
    }

    public PEnumComboBox<Instrument> getInstrument()
    {
        return instrument;
    }

    public PEnumComboBox<Period> getPeriod()
    {
        return period;
    }

    public JXDatePicker getStartDate()
    {
        return startDate;
    }
}