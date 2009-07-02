package com.siebentag.fx.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.siebentag.fx.TickDataPoint;

@Component("tickWriter")
public class TickDataWriter
{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");

	private File outputFile;
	private PrintWriter out;
	private boolean appending = false;
	private String lastHash = "";
	
	public void dataReadEvent(TickDataPoint data)
    {
		load(data);
    }

	public void setOutputFile(File file)
	{
		this.outputFile = file;
	}
	
	public File getOutputFile()
	{
		return outputFile;
	}
	
	public void setAppending(boolean appending)
	{
		this.appending  = appending;
	}
	
	public void prepare() throws Exception
	{
		out = new PrintWriter(new FileOutputStream(outputFile, appending));
	}
	
	public void complete() throws Exception
	{
		out.close();
	}
	
	public void load(TickDataPoint data)
	{
		String hash = data.getDataSource() + "-" + data.getInstrument() + "-" + dateFormat.format(data.getDate()) + "-" + timeFormat.format(data.getDate()); 
		
		if(!hash.equals(lastHash))
		{
			out.print(data.getDataSource());
			out.print(",");
			out.print(data.getInstrument());
			out.print(",");
			out.print(dateFormat.format(data.getDate()));
			out.print(" ");
			out.print(timeFormat.format(data.getDate()));
			out.print(",");
			out.print(timeFormat.format(data.getDate()));
			out.print(",");
			out.print(data.getBuy());
			out.print(",");
			out.print(data.getSell());
			out.print(",");
			out.print(data.getBuyVolume());
			out.print(",");
			out.print(data.getSellVolume());
			out.print("\n");
			
			lastHash = hash;
		}
		else
		{
//			System.out.println("Duplicate entry: " + hash);
		}
	}
}
