package com.jeff.fx.gui.beanform;

import com.jeff.fx.common.TimeOfWeek;

public class BeanPropertyEditorFactory
{

    @SuppressWarnings("unchecked")
    public static BeanPropertyEditor build(Class<?> forClass, String label, Object value)
    {
        if (forClass == Integer.class || forClass == int.class)
        {
            return new IntPropertyEditor(label, value);
        }
        else if (forClass.isEnum())
        {
            return new EnumPropertyEditor((Class<? extends Enum<?>>) forClass, label, value);
        }
        else if(forClass == Boolean.class || forClass == boolean.class)
        {
            return new BooleanPropertyEditor(label, value);
        }
        else if(forClass == TimeOfWeek.class)
        {
            return new TimeOfWeekPropertyEditor(label, value);
        }
        else
        {
            return new BeanPropertyEditor(label, value);
        }
    }
}
