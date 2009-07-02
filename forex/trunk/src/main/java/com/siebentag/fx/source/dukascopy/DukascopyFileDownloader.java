package com.siebentag.fx.source.dukascopy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.util.Config;
import com.siebentag.util.Downloader;
import com.siebentag.util.FileUtil;
import com.siebentag.util.ZipUtil;


public class DukascopyFileDownloader
{
	private static String tempDirectory = Config.getOutputDir() + "/dukascopy/tick/zipped/";
	
	public DukascopyFileDownloader()
	{
	}
	
	public static void main(String[] args)
    {
		List<TickDataPoint> list = downloadDailyTicks(new Date(109, 2, 24), "EURUSD");
		System.out.println(list.size());
    }
	
	public static List<TickDataPoint> downloadDailyTicks(Date date, String instrument)
	{
		List<TickDataPoint> tdps = new ArrayList<TickDataPoint>(50000);
		
		for(int hr=0; hr<24; hr++)
		{
			try
			{
				tdps.addAll(downloadHourlyTicks(date, instrument, hr));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return tdps;
	}
	
	private static File generateOutputFile(String instrument, Date date, int hr)
	{
		return new File(tempDirectory, DukascopyScripter.generateOutputFile(instrument, date, hr));
	}
	
	public static List<TickDataPoint> downloadHourlyTicks(Date date, String instrument, int hr)
		throws Exception
	{
		System.out.println("Downloading ticks for " + instrument + " for " + hr + "h on " + date);
		
		byte[] bytes = Downloader.download(DukascopyScripter.generateUrl(instrument, date, hr));
		
		if(bytes.length > 0)
		{
			File outputFile = generateOutputFile(instrument, date, hr);
			FileUtil.saveFile(bytes, outputFile);
			bytes = ZipUtil.unzipFile(outputFile);
			
			List<TickDataPoint> list = DukascopyBinaryTickReader.parseBytes(bytes, instrument);
			System.out.printf("  %.1fkb read, %d records\n", (double)bytes.length / 1024.0, list.size());
			return list;
		}
		else
		{
			return Collections.<TickDataPoint>emptyList();
		}
	}
}
