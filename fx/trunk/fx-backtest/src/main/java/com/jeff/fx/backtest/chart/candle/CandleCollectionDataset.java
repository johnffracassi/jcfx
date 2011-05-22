package com.jeff.fx.backtest.chart.candle;

import com.jeff.fx.common.CandleCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.joda.time.LocalDateTime;

public class CandleCollectionDataset extends DefaultHighLowDataset {

	private static final long serialVersionUID = -4735041125268894395L;
	private CandleCollection candles;
	
	public CandleCollectionDataset(Comparable<?> seriesKey, CandleCollection cc)
    {
		super(seriesKey, cc.getRawCandleDates(), cc.getRawValuesAsDouble(1), cc.getRawValuesAsDouble(2), cc.getRawValuesAsDouble(0), cc.getRawValuesAsDouble(3), new double[cc.getCandleCount()]);
		this.candles = cc;
	}
	
	public double getXValue(int series, int item)
    {
		if ((item < 0) || (item >= getItemCount(series)))
            return Double.NaN;
        
        return item;
    }
 
	public double getDisplayXValue(int series, int item)
    {
		if ((item < 0) || (item >= getItemCount(series)))
            return Double.NaN;
        
        return super.getXValue(series, item);
    }
    
    public int findFirstXValueGE(long timeval)
    {
    	LocalDateTime ldt = new LocalDateTime(timeval);
    	return candles.getCandleIndex(ldt);
    }
    
    public int findLastXValueLE(long timeval)
    {
    	LocalDateTime ldt = new LocalDateTime(timeval);
    	return candles.getCandleIndex(ldt);
    }
}

