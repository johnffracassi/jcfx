package com.jeff.fx.datastore.file;

import java.io.File;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public class Locator 
{
	private String dataRoot = "c:/dev/jeff/cache/fx";
	private String extension = "txt";

	public File locate(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period)
	{
		String pathStr = String.format("/%s/%s/%04d/%02d/%02d/%02d", dataSource, instrument, dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), dateTime.getHourOfDay());
		String filename = String.format("%s.%s", period.key, getExtension());
		
		return new File(dataRoot + pathStr, filename);
	}
	
	public String getDataRoot()
	{
		return dataRoot;
	}

	public void setDataRoot(String dataRoot)
	{
		this.dataRoot = dataRoot;
	}

	public String getExtension()
	{
		return extension;
	}

	public void setExtension(String extension)
	{
		this.extension = extension;
	}

}
