package com.siebentag.fx.tbv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.siebentag.fx.backtest.OrderSide;
import com.siebentag.fx.mv.VerticalFlowLayout;
import com.siebentag.fx.source.Instrument;

public class TBVDataSetup extends JPanel
{
	private TimeBasedVisualiser tbv;
	private DateSlider pnlStartDate;
	private TimeSlider pnlStartTime;
	private TimeSlider pnlEndTime;
	private JSlider stopSlider;
	private JComboBox cboInstrument = new JComboBox(Instrument.values());
	private JComboBox cboOrderSide = new JComboBox(OrderSide.values());
	
	public TBVDataSetup(final TimeBasedVisualiser tbv)
	{
		setLayout(new VerticalFlowLayout(2));
		this.tbv = tbv;
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				tbv.reloadData(getInstrument());
			}
		});
		
		JPanel pnlInstrument = new JPanel();
		pnlInstrument.add(new JLabel("Instrument"));
		pnlInstrument.add(cboInstrument);
		pnlInstrument.add(cboOrderSide);
		pnlInstrument.add(btnLoad);

		JPanel pnlStop = new JPanel();
		final JLabel lblStop = new JLabel("0.02");
		stopSlider = new JSlider(0, 300, 200);
		pnlStop.add(new JLabel("Stop"));
		pnlStop.add(stopSlider);
		pnlStop.add(lblStop);
		stopSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				lblStop.setText(String.format("%.4f", getStop()));
			}
		});
		
		pnlStartDate = new DateSlider("Start Date");
		pnlStartDate.setDateRange(new LocalDate(2008, 1, 1), new LocalDate());
		
		pnlStartTime = new TimeSlider("Start Time");
		pnlEndTime = new TimeSlider("End Time");

		pnlStartTime.setPeriod(15);
		pnlEndTime.setPeriod(15);
		
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				parametersChanged();
			}
		};
		
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				parametersChanged();
			}
		};
		
		pnlStartDate.addChangeListener(listener);
		pnlStartTime.addChangeListener(listener);
		pnlEndTime.addChangeListener(listener);
		stopSlider.addChangeListener(listener);
		cboOrderSide.addItemListener(itemListener);
		
		add(pnlInstrument);
		add(pnlStartDate);
		add(pnlStartTime);
		add(pnlEndTime);
		add(pnlStop);
	}
	
	public double getStop()
	{
		return (double)stopSlider.getValue() / 10000.0;
	}
	
	public OrderSide getOrderSide()
	{
		return (OrderSide)cboOrderSide.getSelectedItem();
	}
	
	public Instrument getInstrument()
	{
		return (Instrument)cboInstrument.getSelectedItem();
	}
	
	public LocalDate getStartDate()
	{
		return pnlStartDate.getDate();
	}
	
	public LocalTime getStartTime()
	{
		return pnlStartTime.getTime();
	}
	
	public LocalTime getEndTime()
	{
		return pnlEndTime.getTime();
	}
	
	public void parametersChanged()
	{
		tbv.parametersChanged(this);
	}
}
