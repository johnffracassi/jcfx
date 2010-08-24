package com.jeff.fx.gui.beanform;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.jeff.fx.gui.field.IntTextField;

public class IntPropertyEditor extends BeanPropertyEditor {

	private static final long serialVersionUID = -7069845848920302155L;

	public IntPropertyEditor(String label, Object val) {
		super(label, val);
	}

	public Component buildValueComponent(Object initialValue) {
		final IntTextField field = new IntTextField();
		field.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(field.isValidValue()) {
					setValue(field.getValue());
					valueChanged();
				}
			}
		});
		return field;
	}
}
