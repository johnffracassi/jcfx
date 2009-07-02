package com.siebentag.fx.loader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class GenericLineReader
{
	private int count = 0;
	
	public abstract void line(String str, int count);
	
	public void readFile(File file) throws IOException
	{
		count = 0;
		
		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in, getCharEncoding()));
	    
	    String strLine;
		while ((strLine = br.readLine()) != null)   
		{
			count ++;
			line(strLine, count);
		}
	    
		in.close();
	}
	
	public String getCharEncoding()
	{
		return "UTF8";
	}
}
