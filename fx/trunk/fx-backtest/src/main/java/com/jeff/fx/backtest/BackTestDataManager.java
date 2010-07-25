package com.jeff.fx.backtest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataStoreImpl;

@Component
public class BackTestDataManager {
	
	@Autowired
	private DataStoreImpl dataManager;
	
	public List<CandleDataPoint> getCandles() throws Exception {
		
		FXDataSource dataSource = FXDataSource.valueOf(AppCtx.retrieve("newChart.dataSource"));
		Instrument instrument = Instrument.valueOf(AppCtx.retrieve("newChart.instrument"));
		Period period = Period.valueOf(AppCtx.retrieve("newChart.period"));
		FXDataRequest request = new FXDataRequest(dataSource, instrument, AppCtx.retrieveDate("newChart.startDate"), AppCtx.retrieveDate("newChart.endDate"), period);
		
		FXDataResponse<CandleDataPoint> candles = dataManager.loadCandles(request);
		return candles.getData();
	}
}
