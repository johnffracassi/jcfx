package com.jeff.fx.datastore;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.datastore.file.Locator;

public abstract class AbstractDataStore<T extends FXDataPoint> implements DataStore<T> 
{
	private static Logger log = Logger.getLogger(AbstractDataStore.class);

	private Locator locator;
	
	public abstract FXDataResponse<T> load(FXDataRequest request) throws Exception; 
	public abstract void store(List<T> data) throws Exception; 

	public boolean exists(FXDataRequest request)
	{
		File file = locator.locate(request);
		
		boolean exists = file.exists();

		log.debug("file " + (exists?"":"do not") + " exist in data store");
		
		return exists;
	}

	public Locator getLocator() 
	{
		return locator;
	}

	public void setLocator(Locator locator) 
	{
		this.locator = locator;
	}
}
