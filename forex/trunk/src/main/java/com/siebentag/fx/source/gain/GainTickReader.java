package com.siebentag.fx.source.gain;

import java.text.SimpleDateFormat;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.loader.FXFileReader;
import com.siebentag.fx.loader.GenericLineReader;
import com.siebentag.fx.loader.TickDataWriter;
import com.siebentag.fx.source.FXDataSource;


public class GainTickReader extends GenericLineReader implements FXFileReader
{
	private SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private TickDataWriter listener;
	
	public void line(String str, int count)
    {
		// 694630840,USD/JPY,2009-02-08 17:00:22.000,91.730000,91.840000,D

		try
		{
			String[] fields = str.split(",");

			if(fields.length != 6)
			{
				System.out.println("Bad data line " + count + ": " + str.substring(0, 200));
			}
			else
			{
				TickDataPoint dp = new TickDataPoint();
	
				dp.setDataSource(FXDataSource.GAIN);
				dp.setInstrument(fields[1].replaceAll("/", ""));
				
				if(fields[2].length() == 19)
				{
					dp.setDate(df2.parse(fields[2]));
				}
				else
				{
					dp.setDate(df1.parse(fields[2]));
				}
				
				dp.setSell(Double.parseDouble(fields[3]));
				dp.setBuy(Double.parseDouble(fields[4]));
	
				if(listener != null)
				{
					listener.dataReadEvent(dp);
				}
				else
				{
					System.err.println("warning: no listener specified");
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
    }

	public TickDataWriter getListener()
    {
    	return listener;
    }

	public void setListener(TickDataWriter listener)
    {
    	this.listener = listener;
    }
	
	public String getCharEncoding()
	{
		return "UTF8";
	}
}
