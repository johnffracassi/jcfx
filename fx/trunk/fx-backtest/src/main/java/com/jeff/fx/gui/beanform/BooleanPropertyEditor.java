package com.jeff.fx.gui.beanform;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

public class BooleanPropertyEditor extends BeanPropertyEditor {

	private static final long serialVersionUID = -7069845848920302155L;

	public BooleanPropertyEditor(String label, Object val) {
		super(label, val);
	}

	public Component buildValueComponent(Object initialValue) 
	{
	    final JCheckBox field = new JCheckBox();
	    field.setSelected((Boolean)initialValue);
		
	    field.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e)
            {
                setValue(field.isSelected());
            }
        });
	    
		return field;
	}
}
