package com.siebentag.cj.time;

import org.springframework.stereotype.Component;

@Component
public class RealTimeKeeper implements TimeKeeper
{
	private long startTime = -1;
	
	public RealTimeKeeper()
	{
	}
	
    public double getTime()
    {
	    if(startTime < 0)
	    {
			startTime = System.nanoTime();
	    }

	    return (System.nanoTime() - startTime) / 1000000000.0;
    }
}
