package com.jeff.fx.datastore;

import java.util.List;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;

public class AbstractDataStore<T extends FXDataPoint> implements DataStore<T> 
{
	public boolean exists(FXDataRequest request) 
	{
		return false;
	}

	public FXDataResponse<T> load(FXDataRequest request) throws Exception 
	{
		return null;
	}

	public void store(List<T> data) throws Exception 
	{
	}
}
