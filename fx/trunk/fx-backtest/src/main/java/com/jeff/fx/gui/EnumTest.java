package com.jeff.fx.gui;

import java.util.Arrays;

import com.jeff.fx.common.Instrument;

public class EnumTest<T extends Enum<T>> {

	private Class<T> type;
	
	public static void main(String[] args) {
		EnumTest<Instrument> et = new EnumTest<Instrument>(Instrument.class);
		System.out.println(et.resolve("AUDUSD"));
		System.out.println(Arrays.toString(et.values()));
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
	
	@SuppressWarnings("unchecked")
	public T[] values() {
		try {
			return (T[])type.getMethod("values").invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EnumTest(Class<T> type) {
		this.type = type;
	}
}
