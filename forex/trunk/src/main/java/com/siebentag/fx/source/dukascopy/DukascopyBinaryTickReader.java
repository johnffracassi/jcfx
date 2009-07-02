package com.siebentag.fx.source.dukascopy;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.loader.TickDataWriter;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.util.Config;
import com.siebentag.util.ZipUtil;

public class DukascopyBinaryTickReader 
{
	private static String root = Config.getInputDir() + "/dukascopy/tick";

	public static void main(String[] args) 
	{
		try
		{
			TickDataWriter tdw = new TickDataWriter();
			tdw.setAppending(true);

			for(File file : new File(root).listFiles(new BinFilenameFilter()))
			{
				tdw.setOutputFile(new File(root, file.getName().substring(0, 15) + ".csv"));
				tdw.prepare();

				System.out.println("Reading file " + file.getName());
				List<TickDataPoint> tdps = readFile(file);
	
				System.out.println("  Loading " + tdps.size() + " records to file " + tdw.getOutputFile());
				for(TickDataPoint tdp : tdps)
				{
					tdw.load(tdp);
				}
			}
			
			tdw.complete();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static List<TickDataPoint> parseBytes(byte[] bytes, String instrument)
	{
		List<TickDataPoint> ticks = new ArrayList<TickDataPoint>(5000);

		int records = 0;
		
		for(int i=0; i<bytes.length - 40; i++)
		{
			if(bytes[i] == (byte)0 && bytes[i+1] == (byte)0)
			{
				TickData dtick = new TickData();
				dtick.fromBytes(bytes, i);

				if(dtick.ask > 0.01 && dtick.bid > 0.01 && dtick.ask < 2000.0 && dtick.bid < 2000.0 && dtick.bidVol > 0.01 && dtick.askVol > 0.01 && dtick.time > 950000000000l && dtick.time < 1420000000000l)
				{
					records++;

					TickDataPoint tdp = new TickDataPoint();
					tdp.setBuy(dtick.ask);
					tdp.setSell(dtick.bid);
					tdp.setDataSource(FXDataSource.Dukascopy);
					tdp.setDate(new Date(dtick.time));
					tdp.setInstrument(instrument);
					tdp.setBuyVolume((long)(dtick.bidVol));
					tdp.setSellVolume((long)(dtick.askVol));
					ticks.add(tdp);
				}
			}
		}
		
		return ticks;
	}
	
	public static List<TickDataPoint> readFile(File file) throws IOException
	{
		byte[] bytes = ZipUtil.unzipFile(file);
		return parseBytes(bytes, file.getName().substring(0, 6));
	}
}

class DukascopyFilenameFilter implements FilenameFilter
{
	String instrument;
	String date;
	
	public DukascopyFilenameFilter(String instrument, String date)
	{
		this.instrument = instrument;
		this.date = date;
	}
	
	public boolean accept(File dir, String name) 
	{
        return (name.startsWith(instrument + "-" + date) && name.endsWith(".bin"));
    }
}

class BinFilenameFilter implements FilenameFilter
{
	public boolean accept(File dir, String name) 
	{
        return name.endsWith(".bin");
    }
}