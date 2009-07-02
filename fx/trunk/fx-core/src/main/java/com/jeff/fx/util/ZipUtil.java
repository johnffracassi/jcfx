package com.jeff.fx.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtil
{
	public static byte[] unzipFile(File file) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		ZipInputStream zis = new ZipInputStream(fis);
		ZipEntry ze = zis.getNextEntry();		
		ZipFile zf = new ZipFile(file);
		InputStream is = zf.getInputStream(ze);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		
		int bytesRead = 0;
		byte[] bytes = new byte[4096];
		while((bytesRead = is.read(bytes)) > 0)
		{
			baos.write(bytes, 0, bytesRead);
		}
		
		return baos.toByteArray();
	}

	public static byte[] compressByteArray(byte[] input) throws Exception 
    {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);

        // Give the compressor the data to compress
        compressor.setInput(input);
        compressor.finish();

        // Create an expandable byte array to hold the compressed data.
        // You cannot use an array that's the same size as the orginal because
        // there is no guarantee that the compressed data will be smaller than
        // the uncompressed data.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

        // Compress the data
        byte[] buf = new byte[1024];
        while (!compressor.finished()) 
        {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        
        try 
        {
            bos.close();
        } 
        catch (IOException e) 
        {
        }

        // Get the compressed data
        byte[] compressedData = bos.toByteArray();
        return compressedData;
    }
    
    public static byte[] uncompressByteArray(byte[] compressedData) throws Exception 
    {
    	System.out.println("  uncompressing byte array (" + compressedData.length + "b)");
    	
        // Create the decompressor and give it the data to compress
        Inflater decompressor = new Inflater(false);
        decompressor.setInput(compressedData);

        // Create an expandable byte array to hold the decompressed data
        ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);

        // Decompress the data
        byte[] buf = new byte[4096];
        try 
        {
            while (!decompressor.finished()) 
            {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
                System.out.println("    decompressed " + count + "b");
            } 
        }
        catch(Exception e) 
        {
        	e.printStackTrace();
        }
        
        try 
        {
            bos.close();
        } 
        catch (IOException e) 
        {
        }

        // Get the decompressed data
        byte[] decompressedData = bos.toByteArray();

        System.out.println("  uncompressed byte array (" + decompressedData.length + "b)");
        
        return decompressedData;
    }
}
