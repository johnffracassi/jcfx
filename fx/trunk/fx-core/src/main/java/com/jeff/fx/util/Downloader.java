package com.jeff.fx.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;

@Component
public class Downloader
{
	public static byte[] download(String urlStr)
	    throws MalformedURLException, IOException
	{
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream(4 * 1024);
		
		try
		{
			URL url = new URL(urlStr);
			URLConnection urlc = url.openConnection();
	
			bis = new BufferedInputStream(urlc.getInputStream());
	
			int i;
			while ((i = bis.read()) > -1)
			{
				bos.write(i);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (bis != null)
			{
				try
				{
					bis.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
			
			if (bos != null)
			{
				try
				{
					bos.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
		
		return bos.toByteArray();
	}
}
