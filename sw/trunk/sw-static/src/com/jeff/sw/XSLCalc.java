package com.jeff.sw;

public class XSLCalc
{
	int total = 0;
	int count = 0;
	
	public XSLCalc()
	{
		count = 0;
		total = 0;
	}
	
	public void add(int score)
	{
		total += score;
		count ++;
	}
	
	public double avg()
	{
		if(count == 0)
			return 0.0;
		
		return (double)total / (double)count;
	}
	
	public double count()
	{
		return count;
	}
	
	public double total()
	{
		return total;
	}
}
