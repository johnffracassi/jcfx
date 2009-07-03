package com.jeff.fx.datastore.file;

import java.io.File;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.TimeUnit;

public class Locator 
{
	// TODO move this to a config file
	private String dataRoot = "/dev/jeff/fx";
	
	public File locate(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, TimeUnit unit)
	{
		String pathStr = String.format("/%s/%s/%4d/%2d/%2d/%2d", dataSource, instrument, dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), dateTime.getHourOfDay());
		String filename = String.format("%s.txt", unit);
		
		return new File(dataRoot + pathStr, filename);
	}
}
