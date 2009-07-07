package com.jeff.fx.datasource;

import java.io.IOException;

import com.jeff.fx.util.Cache;
import com.jeff.fx.util.DownloadUtil;
import com.jeff.fx.util.Downloader;

public class DownloaderImpl implements Downloader {

	private Cache<byte[]> cache;
	
	public DownloaderImpl(Cache<byte[]> cache)
	{
		this.cache = cache;
	}
	
	public byte[] download(String urlStr) throws IOException {

		byte[] data = DownloadUtil.download(urlStr);
		cache.store(urlStr, data);
		return data;
	}
}
