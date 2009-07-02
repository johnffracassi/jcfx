package com.siebentag.fx.source.dukascopy;

import java.io.File;

import com.siebentag.util.Config;
import com.siebentag.util.FileUtil;

public class FileWriterTask extends AbstractTask<File>
{
	public FileWriterTask(Task task)
	{
		setNextTask(task);
	}
	
	@Override
    public File perform(Task task) throws Exception
    {
		FileData data = (FileData)task.getResult();
		
		File file = new File(Config.getOutputDir() + "/dukascopy/archive/", data.getFilename());
		file.getParentFile().mkdirs();
		
		System.out.println("FileWriterTask: Writing " + (data.getContent().length/1024) + "kb to " + file.getName());
		FileUtil.saveFile(data.getContent(), file);
		
		return file;
    }
}
