package com.jeff.fx.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DownloadUtil {
	private static Logger log = Logger.getLogger(DownloadUtil.class);

	public static byte[] download(String urlStr) throws MalformedURLException, IOException {
		
		log.info("Downloading '" + urlStr + "'");

		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream(4 * 1024);

		try {
			// URL encoding
			urlStr = urlStr.replace(" ", "%20");
			URL url = new URL(urlStr);
			URLConnection urlc = url.openConnection();
			urlc.setConnectTimeout(5);

			// open the input stream
			bis = new BufferedInputStream(urlc.getInputStream());

			// read until stream is exhausted
			int i;
			while ((i = bis.read()) > -1) {
				bos.write(i);
			}
			
		} catch (Exception ex) {
			
			log.error("Error downloading '" + urlStr + "'", ex);
			
		} finally {
			
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

			if (bos != null) {
				try {
					bos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return bos.toByteArray();
	}
}
