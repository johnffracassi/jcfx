package com.jeff.fx.gui.beanform;

import java.awt.Component;

import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.rules.TimeOfWeekEditorController;
import com.jeff.fx.rules.ValueChangeListener;

public class TimeOfWeekPropertyEditor extends BeanPropertyEditor {

	private static final long serialVersionUID = -7069845848920302155L;

	public TimeOfWeekPropertyEditor(String label, Object val) {
		super(label, val);
	}

	public Component buildValueComponent(Object initialValue) 
	{
		final TimeOfWeekEditorController controller = new  TimeOfWeekEditorController();
		controller.setTimeOfWeek((TimeOfWeek)initialValue);

		controller.setListener(new ValueChangeListener() {
            public void valueChanged(Object source)
            {
                setValue(controller.getTimeOfWeek());
                TimeOfWeekPropertyEditor.this.valueChanged();
            }
        });
		
		return controller.getView();
	}
}
