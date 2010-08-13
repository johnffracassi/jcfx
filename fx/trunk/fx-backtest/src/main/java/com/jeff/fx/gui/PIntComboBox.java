package com.jeff.fx.gui;


@SuppressWarnings("serial")
public class PIntComboBox extends PComboBox {
	
	public Object resolve(String str) {
		return new Integer(str);
	}
	
	public PIntComboBox(final String key, final Object ... values) {
		super(key, values);
	}
}

