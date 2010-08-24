package com.jeff.fx.gui.field;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class EnumComboBox<T extends Enum<T>> extends JComboBox {
	
	private Class<T> type;
	
	public EnumComboBox(Class<T> enumClass) {
		this.type = enumClass;
		setModel(new DefaultComboBoxModel(values()));
		setSelectedIndex(-1);
	}
	
	@SuppressWarnings("unchecked")
	public T resolve(String str) {
		
		try {
			return (T)type.getField(str).get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked" })
	private T[] values() {
		
		try {
			return (T[])type.getMethod("values").invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
