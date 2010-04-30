package com.jeff.fx.datasource;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;

public interface DataSource<T extends FXDataPoint> 
{
	public FXDataResponse<T> load(FXDataRequest request) throws Exception;
}
