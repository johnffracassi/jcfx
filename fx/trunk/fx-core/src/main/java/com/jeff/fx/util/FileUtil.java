package com.jeff.fx.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static void saveFile(byte[] bytes, File file) throws IOException {
		// create directories if they don't exists
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		// write file data to disk
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	public static byte[] readBinaryFile(File file) throws IOException {
		return read2array(file);
	}

	/**
	 * Reads a file storing intermediate data into a list. Fast method.
	 * 
	 * @param file
	 *            the file to be read
	 * @return a file data
	 */
	private static byte[] read2list(File file) throws IOException {
		InputStream in = null;
		byte[] buf = null; // output buffer
		int bufLen = 20000 * 1024;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			buf = new byte[bufLen];
			byte[] tmp = null;
			int len = 0;
			List data = new ArrayList(24); // keeps peaces of data
			while ((len = in.read(buf, 0, bufLen)) != -1) {
				tmp = new byte[len];
				System.arraycopy(buf, 0, tmp, 0, len); // still need to do copy
				data.add(tmp);
			}
			/*
			 * This part os optional. This method could return a List data for
			 * further processing, etc.
			 */
			len = 0;
			if (data.size() == 1)
				return (byte[]) data.get(0);
			for (int i = 0; i < data.size(); i++)
				len += ((byte[]) data.get(i)).length;
			buf = new byte[len]; // final output buffer
			len = 0;
			for (int i = 0; i < data.size(); i++) { // fill with data
				tmp = (byte[]) data.get(i);
				System.arraycopy(tmp, 0, buf, len, tmp.length);
				len += tmp.length;
			}
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception e) {
				}
		}
		return buf;
	}

	/**
	 * Reads a file storing intermediate data into an array.
	 * 
	 * @param file
	 *            the file to be read
	 * @return a file data
	 */
	private static byte[] read2array(File file) throws IOException {

		InputStream in = null;
		byte[] out = new byte[0];

		try {
			in = new BufferedInputStream(new FileInputStream(file));

			// the length of a buffer can vary
			int bufLen = 20000 * 1024;
			byte[] buf = new byte[bufLen];
			byte[] tmp = null;
			int len = 0;
			while ((len = in.read(buf, 0, bufLen)) != -1) {

				// extend array
				tmp = new byte[out.length + len];

				// copy data
				System.arraycopy(out, 0, tmp, 0, out.length);
				System.arraycopy(buf, 0, tmp, out.length, len);
				out = tmp;
				tmp = null;
			}
		} finally {
			// always close the stream
			if (in != null)
				try {
					in.close();
				} catch (Exception e) {
				}
		}

		return out;
	}

	public static byte[] readTextFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			throw new IOException("File too large to read");
		}

		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (numRead >= 0) {
			numRead = is.read(bytes, offset, bytes.length - offset);
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		is.close();

		return bytes;
	}
}
