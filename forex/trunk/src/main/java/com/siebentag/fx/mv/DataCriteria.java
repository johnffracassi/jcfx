package com.siebentag.fx.mv;

import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXPanel;
import org.springframework.stereotype.Component;

import com.siebentag.fx.source.Instrument;

@Component("dataCriteria")
public class DataCriteria extends JXPanel
{
	private static final long serialVersionUID = 9019734544365070186L;

	private DateRange dateRange;
	private TimeRange timeRange;
	private IntervalPicker windowSize;
	private JComboBox instrument;

	public DataCriteria()
	{
		dateRange = new DateRange();
		timeRange = new TimeRange();
		windowSize = new IntervalPicker();

		// test data
		Calendar cal = Calendar.getInstance();
		dateRange.addFastDate(cal.getTime());
		cal.set(2009, Calendar.JANUARY, 27);
		dateRange.addFastDate(cal.getTime());
		cal.set(2009, Calendar.MARCH, 26);
		dateRange.addFastDate(cal.getTime());
		// end test data
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(Instrument.values());
		instrument = new JComboBox(model);
		
		init();
	}
	
	private void init()
	{
		setLayout(new VerticalFlowLayout(3));
		setSize(600, 800);
		
		add(dateRange);
		add(timeRange);
		
		JPanel pnlRow = new JPanel();
		pnlRow.add(instrument);
		pnlRow.add(windowSize);
		
		add(pnlRow);
	}

	public Instrument getInstrument()
	{
		return (Instrument)(instrument.getSelectedItem());
	}
	
	public DateRange getDateRange()
	{
		return dateRange;
	}

	public TimeRange getTimeRange()
	{
		return timeRange;
	}

	public IntervalPicker getWindowSize()
	{
		return windowSize;
	}
}