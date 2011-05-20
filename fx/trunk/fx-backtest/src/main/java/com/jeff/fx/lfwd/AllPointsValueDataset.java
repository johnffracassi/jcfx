package com.jeff.fx.lfwd;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import org.jfree.data.xy.DefaultXYDataset;

import java.util.List;

public class AllPointsValueDataset extends DefaultXYDataset {

	private static final long serialVersionUID = -4735041125268894395L;
	private List<CandleDataPoint> values;
    private double initialValue;
    private double initialTime;
    private CandleValueModel cvm = CandleValueModel.Close;

	public AllPointsValueDataset(List<CandleDataPoint> collection)
    {
		super();
        values = collection;
        initialValue = cvm.evaluate(values.get(0));
        initialTime = values.get(0).getDateTime().toDateTime().getMillis() / 60000;
	}

	public double getYValue(int series, int item)
    {
        if(cvm.evaluate(values.get(item)) > 0)
            return cvm.evaluate(values.get(item)) - initialValue;
        else
            return Double.NaN;
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

