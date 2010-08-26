package com.jeff.fx.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
		do {
			numRead = is.read(bytes, offset, bytes.length - offset);
			offset += numRead;
			System.out.println("read " + numRead + " bytes");
		} while (numRead > 0);

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();

		return bytes;
	}
	

	  /**
	   * This convenience method allows to read a
	   * {@link org.apache.commons.fileupload.FileItemStream}'s
	   * content into a string. The platform's default character encoding
	   * is used for converting bytes into characters.
	   * @param pStream The input stream to read.
	   * @see #asString(InputStream, String)
	   * @return The streams contents, as a string.
	   * @throws IOException An I/O error occurred.
	   */
	  public static String asString(InputStream pStream) throws IOException {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      copy(pStream, baos, true);
	      return baos.toString();
	  }
	
	  /**
	   * This convenience method allows to read a
	   * {@link org.apache.commons.fileupload.FileItemStream}'s
	   * content into a string, using the given character encoding.
	   * @param pStream The input stream to read.
	   * @param pEncoding The character encoding, typically "UTF-8".
	   * @see #asString(InputStream)
	   * @return The streams contents, as a string.
	   * @throws IOException An I/O error occurred.
	   */
	  public static String asString(InputStream pStream, String pEncoding) throws IOException {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      copy(pStream, baos, true);
	      return baos.toString(pEncoding);
	  }
	  
	/**
	 * Default buffer size for use in
	 * {@link #copy(InputStream, OutputStream, boolean)}.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	
	/**
	 * Copies the contents of the given {@link InputStream}
	 * to the given {@link OutputStream}. Shortcut for
	 * <pre>
	 *   copy(pInputStream, pOutputStream, new byte[8192]);
	 * </pre>
	 * @param pInputStream The input stream, which is being read.
	 * It is guaranteed, that {@link InputStream#close()} is called
	 * on the stream.
	 * @param pOutputStream The output stream, to which data should
	 * be written. May be null, in which case the input streams
	 * contents are simply discarded.
	 * @param pClose True guarantees, that {@link OutputStream#close()}
	 * is called on the stream. False indicates, that only
	 * {@link OutputStream#flush()} should be called finally.
	 *
	 * @return Number of bytes, which have been copied.
	 * @throws IOException An I/O error occurred.
	 */
	public static long copy(InputStream pInputStream, OutputStream pOutputStream, boolean pClose) throws IOException {
	    return copy(pInputStream, pOutputStream, pClose, new byte[DEFAULT_BUFFER_SIZE]);
	}
	
	/**
	 * Copies the contents of the given {@link InputStream}
	 * to the given {@link OutputStream}.
	 * @param pIn The input stream, which is being read.
	 *   It is guaranteed, that {@link InputStream#close()} is called
	 *   on the stream.
	 * @param pOut The output stream, to which data should
	 *   be written. May be null, in which case the input streams
	 *   contents are simply discarded.
	 * @param pClose True guarantees, that {@link OutputStream#close()}
	 *   is called on the stream. False indicates, that only
	 *   {@link OutputStream#flush()} should be called finally.
	 * @param pBuffer Temporary buffer, which is to be used for
	 *   copying data.
	 * @return Number of bytes, which have been copied.
	 * @throws IOException An I/O error occurred.
	 */
	public static long copy(InputStream pIn, OutputStream pOut, boolean pClose, byte[] pBuffer) throws IOException {
	    OutputStream out = pOut;
	    InputStream in = pIn;
	    try {
	        long total = 0;
	        for (;;) {
	            int res = in.read(pBuffer);
	            if (res == -1) {
	                break;
	            }
	            if (res > 0) {
	                total += res;
	                if (out != null) {
	                    out.write(pBuffer, 0, res);
	                }
	            }
	        }
	        if (out != null) {
	            if (pClose) {
	                out.close();
	            } else {
	                out.flush();
	            }
	            out = null;
	        }
	        in.close();
	        in = null;
	        return total;
	    } finally {
	        if (in != null) {
	            try {
	                in.close();
	            } catch (Throwable t) {
	                /* Ignore me */
	            }
	        }
	        if (pClose  &&  out != null) {
	            try {
	                out.close();
	            } catch (Throwable t) {
	                /* Ignore me */
	            }
	        }
	    }
	}
}
