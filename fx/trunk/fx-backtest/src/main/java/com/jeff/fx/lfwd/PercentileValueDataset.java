package com.jeff.fx.lfwd;

import com.jeff.fx.common.Period;
import org.jfree.data.xy.DefaultXYDataset;

public class PercentileValueDataset extends DefaultXYDataset
{
    Period period;
    double values[];

	public PercentileValueDataset(double[] values, Period period)
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
        return item * period.getInterval() / 60000;
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
		return "Percentile" + series;
	}
}

