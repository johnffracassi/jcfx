package com.siebentag.util;

import java.io.File;

public class Config
{
	public static File getOutputDir()
	{
		if(System.getProperties().get("os.name").equals("Linux"))
		{
			return new File("/home/jeff/forex");
		}
		else
		{
			return new File("c:/dev/fx-data");
		}
	}
	
	public static File getInputDir()
	{
		if(System.getProperties().get("os.name").equals("Linux"))
		{
			return new File("/media/Jeff/dev/fx-data/");
		}
		else
		{
			return new File("x:/dev/fx-data/");
		}
	}
}
