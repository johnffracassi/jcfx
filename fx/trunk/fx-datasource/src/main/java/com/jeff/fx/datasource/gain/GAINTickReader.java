package com.jeff.fx.datasource.gain;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.GenericLineReader;


public class GAINTickReader extends GenericLineReader<TickDataPoint>
{
	private DateTimeFormatter df1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private DateTimeFormatter df2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	
	private String encoding = "UTF8";
	private FXDataSource dataSource = FXDataSource.GAIN;
	
	public FXDataPoint line(String str, int count) throws Exception
    {
		String[] fields = str.split(",");

		if(fields.length != 6)
		{
			System.out.println("Bad data line " + count + ": " + str.substring(0, 200));
			return null;
		}
		else
		{
			TickDataPoint dp = new TickDataPoint();

			dp.setDataSource(getDataSource());
			dp.setInstrument(toInstrument(fields[1]));
			
			if(fields[2].length() == 19)
			{
				dp.setDate(df2.parseDateTime(fields[2]).toLocalDateTime());
			}
			else
			{
				dp.setDate(df1.parseDateTime(fields[2]).toLocalDateTime());
			}
			
			dp.setSell(Double.parseDouble(fields[3]));
			dp.setBuy(Double.parseDouble(fields[4]));

			return dp;
		}
    }
	
	public Instrument toInstrument(String str)
	{
		return Instrument.valueOf(str.replaceAll("/", ""));
	}
	
	public FXDataSource getDataSource()
	{
		return dataSource;
	}
	
	public String getCharEncoding()
	{
		return encoding;
	}
}
