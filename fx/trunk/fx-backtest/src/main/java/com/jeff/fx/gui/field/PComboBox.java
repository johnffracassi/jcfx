package com.jeff.fx.gui.field;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.jeff.fx.backtest.AppCtx;

@SuppressWarnings("serial")
public abstract class PComboBox extends JComboBox {

	private String key;
	
	public abstract Object resolve(String str);

	public PComboBox(final String key) {
		super();
		this.key = key;
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.setPersistent(key, String.valueOf(getSelectedItem()));
			}
		});
	}
	
	public PComboBox(final String key, final Object ... values) {
		this(key);
		setValues(values);
	}
	
	public void setValues(final Object ... values) {
		setModel(new DefaultComboBoxModel(values));
		String val = AppCtx.getPersistent(key);
		setSelectedItem(resolve(val));
	}
}

