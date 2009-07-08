package com.siebentag.cj.game.event;

import com.siebentag.cj.mvc.UmpireState;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("Umpire")
@Consumer("UmpireController")
public class UmpireSignalEvent extends AbstractEvent
{
	UmpireState state;
	
    public UmpireSignalEvent(UmpireState newstate)
	{
    	this.state = newstate;
	}
    
    public UmpireState getState()
    {
    	return state;
    }
    
    public String getDescription()
    {
    	return "Umpire signal event - " + state;
    }
}
