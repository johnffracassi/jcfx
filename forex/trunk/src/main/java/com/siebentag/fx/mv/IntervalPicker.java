package com.siebentag.fx.mv;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXPanel;

import com.siebentag.fx.source.Unit;

public class IntervalPicker extends JXPanel
{
	private static final long serialVersionUID = 11346725625L;

	private JTextField txtAmount;
	private JComboBox cboUnit;
	
	public IntervalPicker()
	{
		setLayout(new FlowLayout(3));
		
		txtAmount = new JTextField("1");
		txtAmount.setColumns(3);
		
		cboUnit = new JComboBox(Unit.values());
		
		add(txtAmount);
		add(cboUnit);
	}
	
	public long getInterval()
	{
		int amount = new Integer(txtAmount.getText());
		long interval = ((Unit)cboUnit.getSelectedItem()).getInterval();
		return amount * interval;
	}
}
