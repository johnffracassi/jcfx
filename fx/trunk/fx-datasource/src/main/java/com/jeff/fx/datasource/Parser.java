package com.jeff.fx.datasource;

import java.util.List;

import com.jeff.fx.common.FXDataPoint;

public interface Parser<T extends FXDataPoint> {
	public List<T> readFile(String data);
}
