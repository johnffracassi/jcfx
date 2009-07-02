package com.siebentag.fx.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.ParseException;

import com.siebentag.fx.DataPointListener;

public abstract class GenericDownloader<T>
{
	private DataPointListener<T> listener;
	
	public GenericDownloader()
	{
	}
	
	public abstract T parse(String line) throws ParseException;
	
	public void download(String url) throws Exception
	{
		URL stream = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(stream.openStream()));

		String inputLine;

		while ((inputLine = in.readLine()) != null)
		{
			try
			{
				T dataPoint = parse(inputLine);
				listener.dataReadEvent(dataPoint);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

		in.close();
	}

	public void downloadFile(String url, File output) throws Exception
	{
		URL stream = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(stream.openStream()));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));

		String inputLine;

		while ((inputLine = in.readLine()) != null)
		{
			try
			{
				out.write(inputLine);
				out.newLine();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

		in.close();
		
		out.flush();
		out.close();
	}

	public DataPointListener<T> getListener()
    {
    	return listener;
    }

	public void setListener(DataPointListener<T> listener)
    {
    	this.listener = listener;
    }
}
