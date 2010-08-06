package com.jeff.fx.datasource.gain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.DataSource;
import com.jeff.fx.datasource.DerivedCandleDataSource;

public class GAINCandleDataSource extends DerivedCandleDataSource {

	public GAINCandleDataSource(DataSource<TickDataPoint> dataSource) {
		super(dataSource);
	}
}
