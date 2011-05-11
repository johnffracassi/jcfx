package com.jeff.fx.filter;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import org.jfree.data.xy.DefaultXYDataset;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class FourPointValueDataset extends DefaultXYDataset {

	private static final long serialVersionUID = -4735041125268894395L;
	private FourPointValue values;

	public FourPointValueDataset(List<CandleDataPoint> collection)
    {
		super();
        values = new FourPointValue(collection);
	}

	public double getYValue(int series, int item)
    {
        if(values.getTime(2) < values.getTime(1))
        {
            if(item == 1 || item == 2)
            {
                return values.getValue(item == 1 ? 2 : 1);
            }
        }

        return values.getValue(item);
	}
	
	public double getXValue(int series, int item)
    {
        if(values.getTime(2) < values.getTime(1))
        {
            if(item == 1 || item == 2)
            {
                return values.getTime(item == 1 ? 2 : 1);
            }
        }

        return values.getTime(item);
    }

	public int getItemCount(int series)
    {
		return 4;
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

class FourPointValue
{
    private double[] values = new double[4];
    private LocalDateTime[] times = new LocalDateTime[4];

    public FourPointValue(List<CandleDataPoint> candles)
    {
        for(CandleDataPoint candle : candles)
        {
            addCandle(candle);
        }
    }

    public double getValue(int item)
    {
        if(item == 0)
            return 0.0;
        else
            return values[item] - values[0];
    }

    public double getTime(int item)
    {
        if(item == 0)
            return 0.0;
        else
            return times[item].toDateTime().getMillis() - times[0].toDateTime().getMillis();
    }

    private void addCandle(CandleDataPoint candle)
    {
        if(times[0] == null)
        {
            times[0] = candle.getDateTime();
            values[0] = candle.getOpen();

            times[1] = times[0].plusMillis((int) candle.getPeriod().getInterval() / 2);
            values[1] = candle.getHigh();

            times[2] = times[0].plusMillis((int) candle.getPeriod().getInterval() / 2);
            values[2] = candle.getLow();

            times[3] = times[0].plusMillis((int)candle.getPeriod().getInterval());
            values[3] = candle.getClose();
        }
        else
        {
            if(candle.getHigh() > values[1])
            {
                times[1] = candle.getDateTime().plusMillis((int) candle.getPeriod().getInterval() / 2);
                values[1] = candle.getHigh();
            }

            if(candle.getLow() > 0 && candle.getLow() < values[2])
            {
                times[2] = candle.getDateTime().plusMillis((int) candle.getPeriod().getInterval() / 2);
                values[2] = candle.getLow();
            }

            times[3] = candle.getDateTime().plusMillis((int) candle.getPeriod().getInterval());
            values[3] = candle.getClose();
        }
    }
}