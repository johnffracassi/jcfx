package com.jeff.fx.backtest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public abstract class PComboBox extends JComboBox {
	
	public abstract Object resolve(String str);
	
	public PComboBox(final Object[] values, final String key) {
		
		super(values);
		
		String val = AppCtx.retrieve(key);
		setSelectedItem(resolve(val));
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.update(key, String.valueOf(getSelectedItem()));
			}
		});
	}
}

