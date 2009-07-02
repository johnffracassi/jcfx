package com.siebentag.fx.source.forexite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.io.FileUtilities;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.collator.CandleStickCollator;
import com.siebentag.fx.loader.CandleDataDAO;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.util.Downloader;
import com.siebentag.util.FileUtil;
import com.siebentag.util.ZipUtil;

@Component
public class ForexiteDownloader
{
	@Autowired
	private CandleDataDAO dao;
	
	public static void main(String[] args)
	{
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    ForexiteDownloader app = (ForexiteDownloader)ctx.getBean("forexiteDownloader");
	    app.run();
	}
	
	public void run()
	{
		LocalDate today = new LocalDateTime().toLocalDate();
//		LocalDate today = new LocalDate(2009, 05, 31);
		LocalDate stopDate = new LocalDate(2009, 06, 01);
		
		while(stopDate.isBefore(today))
		{
			today = today.minusDays(1);
			try
			{
				if(today.getDayOfWeek() != DateTimeConstants.SATURDAY)
				{
					if(!generateOutputFile(today).exists())
					{
						String text = getFileForDate(today);
						
						if(text != null)
						{
							List<CandleStickDataPoint> candles = convertToCandles(text);
							dao.load(candles);
						}
					}
					else
					{
						System.out.println("Skipping " + today + ". Already processed");
					}
				}
			}
			catch(Exception  ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private File generateOutputFile(LocalDate date)
	{
		String outfilename = String.format("/home/jeff/dev/fx-data/forexite/forexite-%02d%02d%02d.zip", date.getDayOfMonth(), date.getMonthOfYear(), date.getYearOfCentury());
		File outfile = new File(outfilename);
		return outfile;
	}
	
	private String generateInputUrl(LocalDate date)
	{
		return String.format("http://www.forexite.com/free_forex_quotes/%d/%02d/%02d%02d%02d.zip", date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), date.getMonthOfYear(), date.getYearOfCentury());
	}
	
	private String getFileForDate(LocalDate date) throws IOException
	{
		File cachedFile = generateOutputFile(date);
		
		if(cachedFile.exists())
		{
			return new String(ZipUtil.unzipFile(cachedFile));
		}
		else
		{
			return downloadFileForDate(date);
		}
	}
	
	private String downloadFileForDate(LocalDate date) throws IOException
	{
		FileOutputStream out = new FileOutputStream(generateOutputFile(date));
		out.write(Downloader.download(generateInputUrl(date)));
		out.close();
		return new String(ZipUtil.unzipFile(generateOutputFile(date)));
	}
	
	private List<CandleStickDataPoint> convertToCandles(String filetext)
	{
		String[] lines = filetext.split("\n");

		// load the 1 minute candles
		List<CandleStickDataPoint> candles = new ArrayList<CandleStickDataPoint>(lines.length); 
		for(int i=1; i<lines.length; i++)
		{
			candles.add(parseCandle(lines[i]));
		}

		// build the multiple minute candles
		candles.addAll(CandleStickCollator.collate(candles, 60, 300));
		candles.addAll(CandleStickCollator.collate(candles, 60, 900));
		candles.addAll(CandleStickCollator.collate(candles, 60, 1800));
		candles.addAll(CandleStickCollator.collate(candles, 60, 3600));
		
		Collections.sort(candles);
		
		return candles;
	}
	
	private CandleStickDataPoint parseCandle(String line)
	{
		String[] cols = line.split(",");
		
		String pair = cols[0];
		LocalDateTime date = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(cols[1] + cols[2]).toLocalDateTime();
		double open = Double.parseDouble(cols[3]);
		double high = Double.parseDouble(cols[4]);
		double low = Double.parseDouble(cols[5]);
		double close = Double.parseDouble(cols[6]);
		
		CandleStickDataPoint candle = new CandleStickDataPoint();
		candle.setDataSource(FXDataSource.Forexite);
		candle.setInstrument(pair);
		candle.setDate(date.toDateTime().toDate());
		candle.setPeriod("60");
		candle.setTickCount(1);
		
		candle.setBuyOpen(open);
		candle.setBuyHigh(high);
		candle.setBuyLow(low);
		candle.setBuyClose(close);
		
		candle.setSellOpen(open);
		candle.setSellHigh(high);
		candle.setSellLow(low);
		candle.setSellClose(close);
		
		return candle;
	}
}
