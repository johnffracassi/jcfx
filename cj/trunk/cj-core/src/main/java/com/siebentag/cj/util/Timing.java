package com.siebentag.cj.util;


public class Timing 
{
	public static void sleep(double length)
    {
    	try
    	{
    		Thread.sleep((long)(length * 1000));
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
}
