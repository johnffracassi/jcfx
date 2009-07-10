package com.jeff.fx.datasource.gain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.DownloadManager;
import com.jeff.fx.util.ZipUtil;

public class GAINDataSource implements DataSource<TickDataPoint> 
{
	private GAINLocator locator;
	private GAINTickReader parser;
	private DownloadManager downloader;

	public static void main(String[] args) throws Exception 
	{
		GAINDataSource gds = new GAINDataSource();
		List<TickDataPoint> ticks = gds.load(Instrument.AUDUSD, new LocalDateTime(2009, 6, 3, 0, 0, 0), Period.Tick);
		
		for(TickDataPoint tick : ticks)
		{
			System.out.println(tick);
		}
	}
	
	public GAINDataSource()
	{
		downloader = new DownloadManager();
		locator = new GAINLocator();
		parser = new GAINTickReader();
	}
	
	public List<TickDataPoint> load(Instrument instrument, LocalDateTime dateTime, Period period) throws Exception 
	{
		List<TickDataPoint> dataPoints = new ArrayList<TickDataPoint>();
		
		List<String> urls = locate(instrument, dateTime, period);
		
		for(String url : urls)
		{
			byte[] compressed = download(url);
			byte[] uncompressed = process(compressed);
			List<TickDataPoint> newPoints = parse(uncompressed);
			dataPoints.addAll(newPoints);
		}
		
		return dataPoints;
	}
	
	public List<String> locate(Instrument instrument, LocalDateTime date, Period period) 
	{
		return locator.generateUrls(instrument, date, period);
	}

	public byte[] download(String url) throws IOException
	{
		return downloader.download(url);
	}
	
	public byte[] process(byte[] data) throws IOException
	{
		return ZipUtil.unzipBytes(data);
	}
	
	public List<TickDataPoint> parse(byte[] data)
	{
		return parser.readFile(new String(data));
	}
}
