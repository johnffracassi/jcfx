package com.jeff.fx.backtest.chart.candle;

import com.jeff.fx.common.CandleCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CandleCollectionDatasetFactory
{
	public CandleCollectionDatasetFactory()
    {
	}

    public DefaultHighLowDataset create(CandleCollection candles)
    {
        Date[] dates = candles.getRawCandleDates();
        double[] opens = candles.getRawValuesAsDouble(0);
        double[] highs = candles.getRawValuesAsDouble(1);
        double[] lows = candles.getRawValuesAsDouble(2);
        double[] closes = candles.getRawValuesAsDouble(3);
        double[] volumes = new double[candles.getCandleCount()];

        DefaultHighLowDataset dataset = new DefaultHighLowDataset("Candles", dates, highs, lows, opens, closes, volumes);
        return dataset;
    }
}

