package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.Period;
import org.jfree.data.xy.DefaultXYDataset;

import java.util.Arrays;
import java.util.List;

public class PercentileValueDataset extends DefaultXYDataset
{
    int percentile;
    Period period;
    double values[];

	public PercentileValueDataset(int percentile, double[] values, Period period)
    {
		super();
        this.period = period;
        this.values = values;
	}

	public double getYValue(int series, int item)
    {
        if(item < values.length)
            return values[item];
        else
            return Double.NaN;
	}
	
	public double getXValue(int series, int item)
    {
        return item * period.getInterval();
    }

	public int getItemCount(int series)
    {
        return values.length;
	}

	public int getSeriesCount()
    {
		return 1;
	}

	public Comparable<?> getSeriesKey(int series)
    {
		return "Percentile " + percentile;
	}
}

