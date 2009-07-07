/**
 * 
 */
package com.siebentag.cj.game.event;

import com.siebentag.cj.model.Team;
import com.siebentag.cj.queue.AbstractEvent;

@Producer("NewMatchAction")
@Consumer("Controllers")
public class TeamChangedEvent extends AbstractEvent
{
	Team bat;
	Team bowl;
	
    public TeamChangedEvent(Team bat, Team bowl)
	{
		this.bat = bat;
		this.bowl = bowl;
	}

	public Team getBattingTeam()
	{
		return bat;
	}

	public void setBat(Team bat)
	{
		this.bat = bat;
	}

	public Team getFieldingTeam()
	{
		return bowl;
	}

	public void setBowl(Team bowl)
	{
		this.bowl = bowl;
	}

	public String getDescription()
    {
	    return "Teams changed (bat=" + bat.getName() + " / bowl=" + bowl.getName() + ")";
    }
}
