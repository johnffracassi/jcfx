package com.jeff.fx.backtest.chart;

import org.jfree.data.xy.DefaultXYDataset;
import org.joda.time.LocalDateTime;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleValueModel;

public class TypicalValueDataset extends DefaultXYDataset {

	private static final long serialVersionUID = -4735041125268894395L;
	private CandleCollection candles;
	
	public TypicalValueDataset(Comparable<?> seriesKey, CandleCollection cc) {
		super();
		this.candles = cc;
	}

	public double getYValue(int series, int item) {
		double price = candles.getPrice(item, CandleValueModel.Typical);
		return (price == 0) ? Double.NaN : candles.getPrice(item, CandleValueModel.Typical);
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
    
    public int findFirstXValueGE(long timeval) {
    	LocalDateTime ldt = new LocalDateTime(timeval);
    	return candles.getCandleIndex(ldt);
    }
    
    public int findLastXValueLE(long timeval) {
    	LocalDateTime ldt = new LocalDateTime(timeval);
    	return candles.getCandleIndex(ldt);
    }

	public int getItemCount(int series) {
		return candles.getCandleCount();
	}

	public int getSeriesCount() {
		return 1;
	}

	public Comparable<?> getSeriesKey(int series) {
		return "Typical Values";
	}
}
