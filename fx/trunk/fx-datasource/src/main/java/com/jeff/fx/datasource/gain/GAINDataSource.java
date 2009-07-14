package com.jeff.fx.datasource.gain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
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

	public FXDataResponse<TickDataPoint> load(FXDataRequest request) throws Exception 
	{
		List<TickDataPoint> dataPoints = new ArrayList<TickDataPoint>();
		
		String url = locate(request.getInstrument(), request.getDate(), request.getPeriod());
		byte[] compressed = download(url);
		byte[] uncompressed = process(compressed);
		List<TickDataPoint> newPoints = parse(uncompressed);
		dataPoints.addAll(newPoints);
		
		return new FXDataResponse<TickDataPoint>(request, newPoints);
	}
	
	public String locate(Instrument instrument, LocalDate date, Period period) 
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
