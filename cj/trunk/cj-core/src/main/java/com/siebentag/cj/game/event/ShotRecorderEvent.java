/**
 * 
 */
package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;

@Producer("ShotRecorder")
@Consumer("")
public class ShotRecorderEvent extends AbstractEvent
{
	boolean starter;
	
	public ShotRecorderEvent(boolean isStarter)
	{
		this.starter = isStarter;
	}
	
    public String getDescription()
    {
	    return "Shot Recorder - " + (starter ? "Start" : "Stop");
    }

    public boolean isStartRecorder()
    {
    	return starter;
    }
    
    public boolean isStopRecorder()
    {
    	return !starter;
    }
}
