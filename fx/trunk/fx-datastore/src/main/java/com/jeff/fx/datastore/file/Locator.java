package com.jeff.fx.datastore.file;

import java.io.File;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public class Locator 
{
	private static Logger log = Logger.getLogger(Locator.class);

	private String dataRoot = "c:/dev/jeff/cache/fx";
	private String extension = "txt";

	public File locate(FXDataSource dataSource, Instrument instrument, LocalDateTime dateTime, Period period)
	{
		String pathStr = String.format("/ser/%s/%s/y%04d/m%02d/d%02d/h%02d", dataSource, instrument, dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), dateTime.getHourOfDay());
		String filename = String.format("%s.%s", period.key, getExtension());
		File store = new File(dataRoot + pathStr, filename);
		
		log.debug("[" + dataSource + "/" + instrument + "/" + period + "/" + dateTime + "] => " + store);
		
		return store;
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
