package com.siebentag.cj.time;

import org.springframework.stereotype.Component;

import com.siebentag.cj.util.math.Time;

@Component
public class RealTimeKeeper implements TimeKeeper
{
	private Long ballStartTime = null;
	private Long appStartTime = null;
	
	public RealTimeKeeper()
	{
	}
	
    public Time getAppTime()
    {
	    if(appStartTime == null)
	    {
			appStartTime = System.nanoTime();
	    }

	    return new Time((System.nanoTime() - appStartTime) / 1000000000.0, Time.Scope.Application);
    }
}
