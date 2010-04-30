package com.jeff.fx.datasource;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jeff.fx.common.FXDataPoint;

public abstract class GenericLineReader<T extends FXDataPoint> {

	public abstract FXDataPoint line(String str, int count) throws Exception;

	@SuppressWarnings("unchecked")
	public List<T> readFile(File file) throws IOException 
	{
		int count = 0;

		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, getCharEncoding()));

		List<T> list = new ArrayList<T>();
		String strLine;
		while ((strLine = br.readLine()) != null) 
		{
			count++;

			try 
			{
				FXDataPoint data = line(strLine, count);

				if (data != null) 
				{
					list.add((T) data);
				}
			} catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}

		in.close();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> readFile(String data) 
	{
		List<T> list = new ArrayList<T>();
		String[] lines = data.split("\n");
		for(int i=0; i<lines.length; i++) 
		{
			try 
			{
				FXDataPoint dataPoint = line(lines[i], i+1);

				if (dataPoint != null) 
				{
					list.add((T)dataPoint);
				}
			} catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}

		return list;	
	}
	
	public String getCharEncoding() 
	{
		return "UTF8";
	}
}
