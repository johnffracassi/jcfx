package com.siebentag.cj.game.event;

import com.siebentag.cj.mvc.ShotResult;
import com.siebentag.cj.queue.AbstractEvent;

public class ShotPlayedEvent extends AbstractEvent
{
	private ShotResult shotResult;
	
	public String getDescription()
    {
	    return "Shot played event";
    }

	public ShotResult getShotResult()
    {
    	return shotResult;
    }

	public void setShotResult(ShotResult shotResult)
    {
    	this.shotResult = shotResult;
    }
}
