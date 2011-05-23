package com.jeff.fx.gui.field;


@SuppressWarnings("serial")
public class PEnumComboBox<T extends Enum<T>> extends PComboBox {
	
	private Class<T> type;
	
	public PEnumComboBox(String key, Class<T> enumClass) {
		
		super(key);
		this.type = enumClass;
		
		setValues((Object[])values());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T resolve(String str) {
		
		try
        {
			return (T)type.getField(str).get(null);
		}
        catch (Exception e)
        {
            System.out.printf("WARNING: Could not resolve %s to a value in %s %n", str, type.getSimpleName());
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private T[] values() {
		
		try
        {
			return (T[])type.getMethod("values").invoke(null);
		}
        catch (Exception e)
        {
			e.printStackTrace();
		}
		
		return null;
	}
}
