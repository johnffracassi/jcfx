package com.siebentag.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil
{
	public static void saveFile(byte[] bytes, File file) throws IOException
	{
		file.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytes);
		fos.close();
	}

	public static byte[] readTextFile(File file) throws IOException 
	{
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE)
		{
			throw new IOException("File too large to read");
		}

		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (numRead >= 0)
		{
			numRead = is.read(bytes, offset, bytes.length - offset);
			offset += numRead;
		}

		if (offset < bytes.length)
		{
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();
		
		return bytes;
	}
}
