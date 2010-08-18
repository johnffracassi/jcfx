package com.jeff.fx.gui;


@SuppressWarnings("serial")
public class PIntComboBox extends PComboBox {
	
	public Object resolve(String str) {
		if(str != null) {
			return new Integer(str);
		} else {
			return 1;
		}
	}
	
	public PIntComboBox(final String key, final Object ... values) {
		super(key, values);
	}
}

