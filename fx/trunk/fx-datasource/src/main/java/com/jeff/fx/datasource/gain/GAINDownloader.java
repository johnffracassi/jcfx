package com.jeff.fx.datasource.gain;

import java.io.File;
import java.io.FileOutputStream;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.util.DownloadUtil;
import com.jeff.fx.util.ZipUtil;


public class GAINDownloader
{
	public static void main(String[] args) throws Exception
	{
		byte[] compressed = DownloadUtil.download("http://ratedata.gaincapital.com/2009/06%20June/AUD_CAD_Week1.zip");
		
		File file = new File("C:/dev/jeff/cache", "aud_cad_w1.zip");
		
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(compressed);
		fos.close();
		
		byte[] uncompressed = ZipUtil.unzipFile(file);
		System.out.println(new String(uncompressed));
	}
	
	public String generateUrl(Instrument instrument, LocalDateTime date, Period period)
	{
		
	}
}	
