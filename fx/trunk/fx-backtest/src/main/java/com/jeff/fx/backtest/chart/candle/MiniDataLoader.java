package com.jeff.fx.backtest.chart.candle;

import com.jeff.fx.common.*;
import com.jeff.fx.datastore.CandleDataStore;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MiniDataLoader
{
    @Autowired
    private CandleDataStore store;

    public List<CandleDataPoint> load(Instrument instrument, Period period, LocalDateTime time, int candlesEitherSide)
    {
        FXDataRequest request = new FXDataRequest(FXDataSource.Forexite, instrument, time.toLocalDate(), period);

        try
        {
            CandleDataResponse response = store.loadCandles(request);
            CandleCollection collection = response.getCandles();
            int idx = collection.getCandleIndex(time);
            return collection.getCandles(idx, candlesEitherSide * 2);

        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
