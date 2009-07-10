package com.jeff.fx.datasource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jeff.fx.util.Downloader;

public class DownloadManager implements Downloader
{
	private Downloader[] sources;

	public static void main(String[] args) throws IOException
	{
		DownloadManager dm = new DownloadManager();
		dm.download("http://ratedata.gaincapital.com/2009/06 June/AUD_CAD_Week1.zip");
	}
	
	public DownloadManager()
	{
		CachedDownloader cache = new CachedDownloader();
		cache.setCacheRoot(new File("c:/dev/jeff/cache/"));
		
		DownloaderImpl downloader = new DownloaderImpl(cache);
		
		sources = new Downloader[] { cache, downloader };
	}
	
	
	public byte[] download(String url) throws IOException 
	{
		for(Downloader source : sources)
		{
			System.out.println("attempting to download using " + source.getClass().getSimpleName());
			
			byte[] data = source.download(url);
			
			if(data != null)
			{
				return data;
			}
		}
		
		throw new FileNotFoundException(url);
	}
}
