package com.jeff.fx.backtest.chart.candle;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CandleListDatasetFactory
{
	public CandleListDatasetFactory()
    {
	}

    public DefaultHighLowDataset create(List<CandleDataPoint> candles)
    {
        Date[] dates = dates(candles);
        double[] opens = values(candles, CandleValueModel.Open);
        double[] highs = values(candles, CandleValueModel.High);
        double[] lows = values(candles, CandleValueModel.Low);
        double[] closes = values(candles, CandleValueModel.Close);
        double[] volumes = new double[candles.size()];

        DefaultHighLowDataset dataset = new DefaultHighLowDataset("Candles", dates, highs, lows, opens, closes, volumes);
        return dataset;
    }

    private double[] values(List<CandleDataPoint> candles, CandleValueModel cvm)
    {
        double[] values = new double[candles.size()];

        int i=0;
        for (CandleDataPoint candle : candles)
        {
            values[i++] = candle.evaluate(cvm);
        }

        return values;
    }

    private Date[] dates(List<CandleDataPoint> candles)
    {
        Date[] dates = new Date[candles.size()];

        int i=0;
        for (CandleDataPoint candle : candles)
        {
            dates[i++] = candle.getDateTime().toDateTime().toDate();
        }

        return dates;
    }
}

