package com.jeff.fx.datastore.file;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {
	private OutputStream out;
	private int buffer;
	private int bitCount;

	public BitOutputStream(OutputStream out) {
		this.out = out;
	}

	synchronized public void writeBit(int bit) throws IOException {
		if (out == null)
			throw new IOException("Already closed");

		if (bit != 0 && bit != 1) {
			throw new IOException(bit + " is not a bit");
		}

		buffer |= bit << bitCount;
		bitCount++;

		if (bitCount == 8) {
			flush();
		}
	}

	private void writeInt(int val) throws IOException
	{
		for(int i=0; i<32; i++)
		{
			buffer |= val << bitCount;
		}

		bitCount += 32;
		
		if (bitCount == 8) {
			flush();
		}
	}
	
	private void flush() throws IOException 
	{
		if (bitCount > 0) 
		{
			out.write((byte) buffer);
			bitCount = 0;
			buffer = 0;
		}
	}

	public void close() throws IOException {
		flush();
		out.close();
		out = null;
	}
}
