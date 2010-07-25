package com.jeff.fx.datasource;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jeff.fx.util.Downloader;

@Component
public class DownloadManager implements Downloader {
	
	private static Logger log = Logger.getLogger(DownloadManager.class);

	private Downloader[] sources;

	public DownloadManager() {
	}

	public byte[] download(String url) throws IOException {
		for (Downloader source : sources) {
			
			log.info("attempting to download using " + source.getClass().getSimpleName());

			byte[] data = source.download(url);

			if (data != null) {
				return data;
			}
		}

		throw new FileNotFoundException(url);
	}

	public Downloader[] getSources() {
		return sources;
	}

	public void setSources(Downloader[] sources) {
		this.sources = sources;
	}
}
