package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;

@Producer("Scorer")
@Consumer("")
public class InningsCompleteEvent extends AbstractEvent
{
    public InningsCompleteEvent()
	{
	}

	public String getDescription()
    {
	    return "Innings complete";
    }
}
