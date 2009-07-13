package com.jeff.fx.datasource.gain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.DataSource;
import com.jeff.fx.util.Downloader;
import com.jeff.fx.util.ZipUtil;

@Component("gain")
public class GAINDataSource implements DataSource<TickDataPoint> 
{
	@Autowired
	private GAINLocator locator;
	
	@Autowired
	private GAINTickReader parser;
	
	@Autowired
	@Qualifier("downloader")
	private Downloader downloader;

	public GAINDataSource()
	{
	}
	
	public List<TickDataPoint> load(Instrument instrument, LocalDateTime dateTime, Period period) throws Exception 
	{
		List<TickDataPoint> dataPoints = new ArrayList<TickDataPoint>();
		
		String url = locate(instrument, dateTime, period);
		byte[] compressed = download(url);
		byte[] uncompressed = process(compressed);
		List<TickDataPoint> newPoints = parse(uncompressed);
		dataPoints.addAll(newPoints);
		
		return dataPoints;
	}
	
	public String locate(Instrument instrument, LocalDateTime date, Period period) 
	{
		return locator.generateUrl(instrument, date, period);
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
