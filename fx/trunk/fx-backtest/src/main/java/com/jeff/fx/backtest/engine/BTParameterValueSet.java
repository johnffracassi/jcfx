package com.jeff.fx.backtest.engine;


public class BTParameterValueSet {

	private double startValue;
	private double endValue;
	private double step;

	public int getNumberOfSteps() {
		return (int) ((endValue - startValue) / step) + 1;
	}

	public double[] expand() {
		double[] values = new double[getNumberOfSteps()];
		
		for(int i=0; i<values.length; i++) {
			values[i] = startValue + ((double)i * step);
		}
		
		return values;
	}
	
	public double getValue(int step) {
		return (startValue + ((endValue - startValue) / (double) step));
	}

	public double getStartValue() {
		return startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}
	
	@Override
	public String toString() {
		return "[" + startValue + "=>" + endValue + " s=" + step + " " + toString(expand()) + "]"; 
	}
	
	private String toString(double[] vals) {
		String str = "";
		for(int i=0; i<vals.length; i++) {
			str += String.valueOf(vals[i]);
			if(i <vals.length-1) {
				str += ", ";
			}
		}
		return "<" + str + ">";
	}
}
