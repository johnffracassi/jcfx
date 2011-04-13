package com.jeff.fx.gui.beanform;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jeff.fx.indicator.ValueListener;

public class BeanPropertyEditor {
	
	private static final long serialVersionUID = -7574130101664081340L;
	
	private String label;
	private Object value;

	private ValueListener listener;
	private Component compLabel;
	private Component compValue;
		
	public BeanPropertyEditor(String label) {
		this.label = label;
		compLabel = new JLabel(getLabel());
	}
	
	public BeanPropertyEditor(String label, Object val) {
		this(label);
		compValue = buildValueComponent(val);
	}

	public ValueListener getListener() {
		return listener;
	}
	
	public void setListener(ValueListener listener) {
		this.listener = listener;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setValue(Object value) {
		this.value = value;
		valueChanged();
	}
	
	public void setValueComponent(Component comp) {
		compValue = comp;
	}
	
	public Component getLabelComponent() {
		return compLabel;
	}
	
	public Component getValueComponent() {
		return compValue;
	}
	
	public void valueChanged() {
		if(getListener() != null) {
			getListener().valueChanged(getValue());
		}
	}
	
	public Component buildValueComponent(Object initialValue) {
		final JTextField field = new JTextField(String.valueOf(initialValue));
		field.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				setValue(field.getText());
				valueChanged();
			}
		});
		field.setColumns(20);
		return field;
	}
}
