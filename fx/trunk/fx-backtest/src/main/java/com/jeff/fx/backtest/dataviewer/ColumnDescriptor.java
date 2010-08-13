package com.jeff.fx.backtest.dataviewer;

import javax.swing.table.TableCellRenderer;

public class ColumnDescriptor {
	
	private String name;
	private Class<?> type;
	private TableCellRenderer renderer;

	public ColumnDescriptor(String name, Class<?> type) {
		this(name, type, null);
	}
	
	public ColumnDescriptor(String name, Class<?> type, TableCellRenderer renderer) {
		this.name = name;
		this.type = type;
		this.renderer = renderer;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public void setType(Class<?> clazz) {
		this.type = clazz;
	}

	public TableCellRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(TableCellRenderer renderer) {
		this.renderer = renderer;
	}
}