package com.jeff.fx.util;

import java.io.IOException;

public class DownloaderImpl implements Downloader {

	public byte[] download(String urlStr) throws IOException {

		return DownloadUtil.download(urlStr);
	}
}
