package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;
import org.jfree.data.xy.DefaultXYDataset;

import java.util.List;

public class AllPointsValueDataset extends DefaultXYDataset {

	private static final long serialVersionUID = -4735041125268894395L;
	private List<CandleDataPoint> values;
    private double initialValue;
    private double initialTime;

	public AllPointsValueDataset(List<CandleDataPoint> collection)
    {
		super();
        values = collection;
        initialValue = values.get(0).getOpen();
        initialTime = values.get(0).getDateTime().toDateTime().getMillis() / 60000;
	}

	public double getYValue(int series, int item)
    {
        return values.get(item).getOpen() - initialValue;
	}
	
	public double getXValue(int series, int item)
    {
        return (values.get(item).getDateTime().toDateTime().getMillis() / 60000) - initialTime;
    }

	public int getItemCount(int series)
    {
		return values.size();
	}

	public int getSeriesCount()
    {
		return 1;
	}

	public Comparable<?> getSeriesKey(int series)
    {
		return "Series #" + series;
	}

    private String getTimeString(double millis)
    {
        int minutes = (int)(millis / 1000 / 60);

        if(minutes > 60)
        {
            return String.format("%dh%dm", minutes/60, minutes%60);
        }
        else
        {
            return String.format("%dm", minutes);
        }
    }
}

