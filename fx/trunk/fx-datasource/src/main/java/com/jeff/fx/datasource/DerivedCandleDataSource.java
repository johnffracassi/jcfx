package com.jeff.fx.datasource;

import java.util.List;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.converter.TickToCandleConverter;

/**
 * Derive candles from a collection of ticks
 * 
 * @author Jeff
 */
public class DerivedCandleDataSource implements DataSource<CandleDataPoint> {

	private DataSource<TickDataPoint> dataSource;

	public DerivedCandleDataSource(DataSource<TickDataPoint> dataSource) {
		this.dataSource = dataSource;
	}

	public FXDataResponse<CandleDataPoint> load(FXDataRequest request)
			throws Exception {

		// get the ticks matching the requested period
		FXDataRequest tickRequest = new FXDataRequest(request.getDataSource(),
				request.getInstrument(), request.getInterval(), Period.Tick);

		FXDataResponse<TickDataPoint> response = dataSource.load(tickRequest);

		// convert the ticks to the requested period
		TickToCandleConverter t2c = new TickToCandleConverter();
		List<CandleDataPoint> candles = t2c.convert(response.getData(), request
				.getPeriod());

		return new FXDataResponse<CandleDataPoint>(request, candles);
	}
}
