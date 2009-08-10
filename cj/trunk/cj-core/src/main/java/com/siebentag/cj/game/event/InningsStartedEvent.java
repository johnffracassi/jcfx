package com.siebentag.cj.game.event;

import com.siebentag.cj.model.Team;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("Scorer")
@Consumer("")
public class InningsStartedEvent extends AbstractEvent
{
	private Team batting;
	private Team fielding;
	
    public InningsStartedEvent(Team batting, Team fielding)
	{
    	this.batting = batting;
    	this.fielding = fielding;
	}

	public Team getBatting() {
		return batting;
	}

	public Team getFielding() {
		return fielding;
	}

	public String getDescription()
    {
	    return "Innings started";
    }
}
