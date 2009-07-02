package com.siebentag.fx.source.gain;

import java.io.File;
import java.io.FilenameFilter;

import com.siebentag.fx.loader.TickDataWriter;

public class GainProcessor 
{
	public static void main(String[] args) 
	{
		String input = "/mnt/sata1500b/dev/forex/gain/2009-04";
		File output = new File("/mnt/sata1500b/dev/forex/gain/clean", "apr09b.csv");
		
		File root = new File(input);
		File[] files = root.listFiles(new FilenameFilter() { 
			public boolean accept(File path, String filename) {
				return filename.toLowerCase().endsWith(".csv");
			} 
		});

		try
		{
			TickDataWriter writer = new TickDataWriter();
			writer.setOutputFile(output);
			writer.prepare();
	
			GainTickReader reader = new GainTickReader();
			reader.setListener(writer);
	
			for(File file : files)
			{
				try
				{
					System.out.println(file);
					reader.readFile(file);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
	
			writer.complete();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
