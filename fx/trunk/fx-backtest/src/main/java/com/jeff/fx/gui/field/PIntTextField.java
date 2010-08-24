package com.jeff.fx.gui.field;

import com.jeff.fx.backtest.AppCtx;

public class PIntTextField extends IntTextField {

	private static final long serialVersionUID = 2462148797142222672L;

	private String key;
	
	public PIntTextField(String key) {
		super();
		this.key = key;
		setValue(AppCtx.getPersistentInt(key, 0));
	}
	
	protected void updateValue() {
		if(isValidValue()) {
			AppCtx.setPersistent(key, getText());
		}
	}
}