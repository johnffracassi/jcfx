package com.jeff.fx.gui.beanform;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jeff.fx.gui.field.EnumComboBox;

public class EnumPropertyEditor extends BeanPropertyEditor {

	private static final long serialVersionUID = -7069845848920302155L;

	private Class<? extends Enum<?>> enumClass;
	
	public EnumPropertyEditor(Class<? extends Enum<?>> forClass, String label, Object val) {
		super(label);
		this.enumClass = forClass;
		setValueComponent(buildValueComponent(val));
	}

	public Component buildValueComponent(Object initialValue) {
		final EnumComboBox ecb = new EnumComboBox(enumClass);
		ecb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				setValue(ecb.getSelectedItem());
				valueChanged();
			}
		});
		return ecb;
	}
}
