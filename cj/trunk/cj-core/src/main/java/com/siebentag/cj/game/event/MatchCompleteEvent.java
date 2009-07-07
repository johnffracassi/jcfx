/**
 * 
 */
package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;


@Producer("Scorer")
@Consumer("")
public class MatchCompleteEvent extends AbstractEvent
{
    public MatchCompleteEvent()
	{
	}

	public String getDescription()
    {
	    return "Match complete";
    }
}
