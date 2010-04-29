package com.jeff.fx.datasource.gain;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.ZippedAbstractDataSource;

@Component("GAINTickDataSource")
public class GAINTickDataSource extends ZippedAbstractDataSource<TickDataPoint> {
}
