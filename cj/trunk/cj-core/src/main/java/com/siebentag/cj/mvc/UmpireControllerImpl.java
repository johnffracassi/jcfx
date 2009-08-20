package com.siebentag.cj.mvc;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.graphics.renderer.UmpireRenderer;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

@Component
public class UmpireControllerImpl extends PlayerControllerImpl implements UmpireController
{
	@Autowired
	private UmpireRenderer renderer;

	private Map<UmpireRole,Player> umpireByRole;;
	private Map<Player,UmpireState> stateByUmpire;
	
	public UmpireControllerImpl()
	{
		umpireByRole = new HashMap<UmpireRole, Player>();
		stateByUmpire = new HashMap<Player, UmpireState>();
	}
	
	public void paint(Graphics2D g, Time time)
    {
		for(UmpireRole role : UmpireRole.values())
		{
			Player umpire = getUmpire(role);
			Point3D sLoc = getLocation(umpire, time);
			renderer.render(g, sLoc, getState(umpire), getTimeSinceStateChange(umpire, time));
		}		
    }

	public Player getUmpire(UmpireRole role)
	{
		return umpireByRole.get(role);
	}

	public void setState(Player umpire, UmpireState umpireState)
	{
		stateByUmpire.put(umpire, umpireState);
	}
	
	public UmpireState getState(Player umpire)
	{
		if(stateByUmpire.containsKey(umpire))
		{
			return stateByUmpire.get(umpire);
		}
		else
		{
			return UmpireState.Idle;
		}
	}
	
	public UmpireState getState(UmpireRole role)
	{
		return getState(getUmpire(role));
	}

	public void setUmpire(Player umpire, UmpireRole role)
	{
		umpireByRole.put(role, umpire);
	}

	public void resetForBall()
    {
		for(UmpireRole role : UmpireRole.values())
		{
			stateByUmpire.put(getUmpire(role), UmpireState.Idle);
		}
    }

	public void resetForInnings(Team battingTeam, Team bowlingTeam)
    {
		Player u1 = new Player();
		u1.setKey("ump1");
		u1.setHjid(1L);
		
		Player u2 = new Player();
		u2.setKey("ump2");
		u2.setHjid(2L);
		
		umpireByRole.put(UmpireRole.MainUmpire, u1);
		umpireByRole.put(UmpireRole.SquareLegUmpire, u2);
		
		setLocation(u1, FieldPosition.UmpireBowler.getLocation());
		setLocation(u2, FieldPosition.UmpireSquareLeg.getLocation());
		
		resetForBall();
    }
}
