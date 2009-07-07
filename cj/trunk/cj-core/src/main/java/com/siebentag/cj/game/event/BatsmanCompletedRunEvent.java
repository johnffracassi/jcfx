package com.siebentag.cj.game.event;

import com.siebentag.cj.mvc.BatsmanRole;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("Umpire")
@Consumer("BatsmanController")
public class BatsmanCompletedRunEvent extends AbstractEvent
{
	private BatsmanRole role;

	public BatsmanCompletedRunEvent(BatsmanRole role)
	{
		this.role = role;
	}
	
	public String getDescription()
    {
	    return "Batsman completed run (" + role + ")";
    }

	public BatsmanRole getRole()
    {
    	return role;
    }
}
