package com.siebentag.fx.loader;

import java.io.File;

public class DirectoryCreate
{
	public static void main(String[] args)
    {
	    File parent = new File("X:/dev/fx-data/gain/archives/");
	    
	    for(int year = 2004; year < 2009; year ++)
	    {
	    	for(int month = 1; month <= 12; month ++)
	    	{
	    		File dir = new File(parent, year + "-" + (month<10?"0":"") + month + "/");
	    	    dir.mkdir();
	    	}
	    }
    }
}
