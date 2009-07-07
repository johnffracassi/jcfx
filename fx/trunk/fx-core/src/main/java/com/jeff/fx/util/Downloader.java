package com.jeff.fx.util;

import java.io.IOException;

public interface Downloader {
	public byte[] download(String url) throws IOException;
}
