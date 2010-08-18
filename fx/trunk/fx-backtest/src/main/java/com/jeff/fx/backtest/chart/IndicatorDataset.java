package com.jeff.fx.backtest.chart;

import org.jfree.data.xy.DefaultXYDataset;

import com.jeff.fx.indicator.Indicator;

public class IndicatorDataset extends DefaultXYDataset {

	private static final long serialVersionUID = -4735041125268894395L;
	private Indicator indicator;
	
	public IndicatorDataset(Comparable<?> seriesKey, Indicator indicator) {
		super();
		this.indicator = indicator;
	}

	public double getYValue(int series, int item) {
		double value = indicator.getValue(item);
		if(value == 0.0) {
			return Double.NaN;
		} else {
			return indicator.getValue(item);
		}
	}
	
	public double getXValue(int series, int item) {
		if ((item < 0) || (item >= getItemCount(series)))
            return Double.NaN;
        return item;
    }
 
	public double getDisplayXValue(int series, int item) {
		if ((item < 0) || (item >= getItemCount(series)))
            return Double.NaN;
        return super.getXValue(series, item);
    }
	
	public int getItemCount(int series) {
		return indicator.getSize();
	}

	public int getSeriesCount() {
		return 1;
	}

	public Comparable<?> getSeriesKey(int series) {
		return "Typical Values";
	}
}
