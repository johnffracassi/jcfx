package com.jeff.fx.datasource;

import java.io.File;
import java.io.IOException;

import com.jeff.fx.util.Cache;
import com.jeff.fx.util.Downloader;
import com.jeff.fx.util.FileUtil;

public class DownloadCache implements Downloader, Cache<byte[]> {

	private File cacheRoot;

	public byte[] download(String url) throws IOException 
	{
		return retrieve(url);
	}

	public void store(String url, byte[] bytes) throws IOException 
	{

		File file = transformUrlToFile(url);
		FileUtil.saveFile(bytes, file);
	}

	public byte[] retrieve(String key) throws IOException 
	{
		if(exists(key))
		{
			return FileUtil.readBinaryFile(transformUrlToFile(key));
		}
		else
		{
			return null;
		}
	}

	public boolean exists(String url) 
	{
		String fileStr = transformUrlToPath(url);
		File file = new File(cacheRoot, fileStr);
		System.out.println(file);
		return file.exists();
	}

	private File transformUrlToFile(String url) 
	{
		return new File(getCacheRoot(), transformUrlToPath(url));
	}

	private String transformUrlToPath(String url) 
	{
		url = url.toLowerCase();

		String path = url.substring(0, url.lastIndexOf('/'));
		String filename = url.substring(url.lastIndexOf('/'));

		path = path.replace("https://", "");
		path = path.replace("http://", "");
		path = path.replaceAll("\\.", "/");

		return path + filename;
	}

	public File getCacheRoot() 
	{
		return cacheRoot;
	}

	public void setCacheRoot(File cacheRoot) 
	{
		this.cacheRoot = cacheRoot;
	}

}
