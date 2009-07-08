package com.siebentag.cj;


public class Config
{
	private static String dataDir;
	
	static
	{
		dataDir = System.getProperty("data");
	}
	
    public static String getDataDir()
    {
    	return dataDir;
    }
}
