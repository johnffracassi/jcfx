package com.jeff.fx.datasource.dukascopy;

import java.util.Calendar;
import java.util.Date;

import com.siebentag.fx.source.DataDownloadJob;
import com.siebentag.util.Downloader;

public class DownloadTask extends AbstractTask<FileData>
{
	private DataDownloadJob job;
	
	public DownloadTask(DataDownloadJob job, Task task)
	{
		this.job = job;
		setNextTask(task);
	}
	
	public FileData perform(Task previousTask) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(job.getStartDate());
		
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		String ins = job.getInstrument().toString();
		Date date = cal.getTime();
		String filename = DukascopyScripter.generateOutputFile(ins, date, hr);	
		String url = DukascopyScripter.generateUrl(ins, date, hr);
		
		System.out.println("DownloadTask: Start download of "+ url);
		
		FileData data = new FileData(Downloader.download(url), filename); 
		
		StatsPanel.addData(data.getContent().length, true);
		
		return data;
	}
}
