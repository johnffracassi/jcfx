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

    public List<CandleDataPoint> load(FXDataSource dataSource, Instrument instrument, Period period, LocalDateTime time, int candlesEitherSide) throws IOException
    {
        FXDataRequest request = new FXDataRequest(dataSource, instrument, time.toLocalDate().minusDays(1), time.toLocalDate().plusDays(1), period);
        CandleDataResponse response = store.loadCandles(request);
        CandleCollection collection = response.getCandles();
        int idx = collection.getCandleIndex(time);
        return collection.getCandles(Math.max(0, idx - candlesEitherSide), Math.min(candlesEitherSide * 2 + 1, collection.getCandleCount()));
    }
}
