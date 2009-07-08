package com.siebentag.cj.util;

import java.util.List;

public class Random 
{
	public static String random(String[] array)
	{
		return array[random(array.length)]; 
	}
	
	public static String random(List<String> list)
	{
		return list.get(random(list.size()));
	}
	
	public static int random(int max)
	{
		return (int)(Math.random() * max);
	}
}
