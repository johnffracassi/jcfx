package com.siebentag.fx.source.dukascopy;

import java.io.File;

import com.siebentag.util.ZipUtil;

public class ZipFileReaderTask extends AbstractTask<FileData>
{
	public ZipFileReaderTask(Task task)
	{
		setNextTask(task);
	}
	
	@Override
    public FileData perform(Task task) throws Exception
    {
		File inputFile = (File)task.getResult();

		System.out.println("ZipFileReaderTask: Reading " + inputFile.getName());
		
		FileData data = new FileData(ZipUtil.unzipFile(inputFile), inputFile.getName());
		
		StatsPanel.addData(data.getContent().length, false);
		
		return data;
    }
}
