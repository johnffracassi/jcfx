package com.jeff.fx.gui.beanform;


public class BeanPropertyEditorFactory {

	@SuppressWarnings("unchecked")
	public static BeanPropertyEditor build(Class<?> forClass, String label, Object value) {
		
		System.out.println(forClass.getName());
		
		if(forClass == Integer.class || forClass == int.class) {
			return new IntPropertyEditor(label, value);
		} else if(forClass.isEnum()) {
			return new EnumPropertyEditor((Class<? extends Enum<?>>)forClass, label, value);
		} else {
			return new BeanPropertyEditor(label, value);
		}
	}
}
