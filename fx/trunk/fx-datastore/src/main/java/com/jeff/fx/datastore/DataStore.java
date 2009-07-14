package com.jeff.fx.datastore;

import java.util.List;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;

public interface DataStore<T extends FXDataPoint> 
{
	/**
	 * 
	 * @param data
	 * @return success indicator
	 */
	public void store(List<T> data) throws Exception;

	/**
	 * 
	 * 
	 * @param dataSource
	 * @param instrument
	 * @param dateTime
	 * @param period
	 * @return
	 * @throws Exception
	 */
	public FXDataResponse<T> load(FXDataRequest request) throws Exception;
	
	/**
	 * 
	 * 
	 * @param dataSource
	 * @param instrument
	 * @param dateTime
	 * @param period
	 * @return
	 */
	public boolean exists(FXDataRequest request);
}
