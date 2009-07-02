package com.siebentag.fx.loader;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.source.DataDownloadJob;
import com.siebentag.util.Config;

@Component("candlestickWriter")
public class CandleDataWriter
{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	private File dataDir = new File(Config.getInputDir() + "/candlestick/dukascopy");
	private PrintWriter out;
	private DataDownloadJob job;
	
	private String lastHash = "";
	
	public void dataReadEvent(CandleStickDataPoint data)
    {
		load(data);
    }

	public void setJob(DataDownloadJob job)
	{
		this.job = job;
	}
	
	public void prepare() throws Exception
	{
//		out = new PrintWriter(new FileOutputStream(new File(dataDir, job.getOutputFilename()), true));
	}
	
	public void complete() throws Exception
	{
		out.close();
	}
	
	public void load(CandleStickDataPoint data)
	{
		String hash = data.getDataSource() + "-" + data.getInstrument() + "-" + dateFormat.format(data.getDate()) + "-" + timeFormat.format(data.getDate()); 
		
		if(!hash.equals(lastHash))
		{
			out.print(data.getDataSource());
			out.print(",");
			out.print(data.getInstrument());
			out.print(",");
			out.print(data.getPeriod());
			out.print(",");
			out.print(dateFormat.format(data.getDate()));
			out.print(",");
			out.print(timeFormat.format(data.getDate()));
			out.print(",");
			out.print(data.getBuyOpen());
			out.print(",");
			out.print(data.getBuyHigh());
			out.print(",");
			out.print(data.getBuyLow());
			out.print(",");
			out.print(data.getBuyClose());
			out.print(",");
			out.print(data.getSellOpen());
			out.print(",");
			out.print(data.getSellHigh());
			out.print(",");
			out.print(data.getSellLow());
			out.print(",");
			out.print(data.getSellClose());
			out.print("\n");
			
			lastHash = hash;
		}
		else
		{
//			System.out.println("Duplicate entry: " + hash);
		}
	}
}
