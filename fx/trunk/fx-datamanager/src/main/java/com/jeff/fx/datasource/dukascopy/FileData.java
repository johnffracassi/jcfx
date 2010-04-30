package com.jeff.fx.datasource.dukascopy;

public class FileData
{
	private byte[] content;
	private String filename;

	public FileData(byte[] content, String filename)
    {
	    super();
	    this.content = content;
	    this.filename = filename;
    }

	public byte[] getContent()
	{
		return content;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

}
