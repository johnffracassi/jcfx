package com.jeff.fx.indicator;

public class IndicatorMarker {
	
	private int x;
	private double y;
	private String label;
	private int labelLocation;
	
	public IndicatorMarker(int x, double y) {
		this(x, y, null, 0);
	}
	
	public IndicatorMarker(int x, double y, String label) {
		this(x, y, label, 1);
	}
	
	public IndicatorMarker(int x, double y, String label, int labelLocation) {
		this.label = label;
		this.labelLocation = labelLocation;
		this.x = x;
		this.y = y;
	}
	
	public String getLabel() {
		return label;
	}

	public int getLabelLocation() {
		return labelLocation;
	}

	public void setLabelLocation(int labelLocation) {
		this.labelLocation = labelLocation;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getIndex() {
		return x;
	}
	
	public double getValue() {
		return y;
	}
}
