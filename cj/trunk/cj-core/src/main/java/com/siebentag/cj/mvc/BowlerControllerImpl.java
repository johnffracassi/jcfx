package com.siebentag.cj.mvc;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.graphics.renderer.BowlerRenderer;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.util.math.Point3D;

@Component
public class BowlerControllerImpl extends PlayerControllerImpl implements BowlerController
{
	@Autowired
	BowlerRenderer renderer;

	Map<BowlerRole,Player> playerByRole;
	Map<Player,BowlerState> stateByPlayer;
	Map<BowlerRole,Integer> runsQueued;
	BowlingSide bowlingSide;
	
	
	public BowlerControllerImpl()
	{
		playerByRole = new HashMap<BowlerRole, Player>();
		stateByPlayer = new HashMap<Player, BowlerState>();
		runsQueued = new HashMap<BowlerRole, Integer>();
	}
	
	public void resetForInnings(Team battingTeam, Team fieldingTeam)
	{
		setBowler(fieldingTeam.getPlayers().getPlayers().get(9), BowlerRole.CurrentBowler);
		setBowler(fieldingTeam.getPlayers().getPlayers().get(8), BowlerRole.OtherBowler);
		
		// TODO set colours for renderer
		
		resetForBall();
	}
	
	public void resetForBall()
	{
		// move the bowler to the start of the run-up
		if(getBowlingSide() == BowlingSide.LeftArmAround || getBowlingSide() == BowlingSide.RightArmOver)
		{
			setLocation(getBowler(BowlerRole.CurrentBowler), FieldPosition.BowlOverFastRunUp.getLocation());
		}
		else
		{
			setLocation(getBowler(BowlerRole.CurrentBowler), FieldPosition.BowlAroundFastRunUp.getLocation());
		}
	}
	
	public BowlingSide getBowlingSide()
	{
		return bowlingSide == null ? BowlingSide.RightArmOver : bowlingSide;
	}
	
	public void setBowlingSide(BowlingSide side)
	{
		this.bowlingSide = side;
	}
	
	public void paint(Graphics2D g, double time)
    {
		Player bowler = getBowler(BowlerRole.CurrentBowler);
		Point3D sLoc = getLocation(bowler, time);
		renderer.render(g, sLoc, getState(bowler), getTimeSinceStateChange(bowler, time));
    }

	public Player getBowler(BowlerRole role)
    {
		return playerByRole.get(role);
    }

	public void setBowler(Player player, BowlerRole role)
    {
	    playerByRole.put(role, player);	    
    }

	public BowlerState getState(Player player)
	{
		return stateByPlayer.containsKey(player) ? stateByPlayer.get(player) : BowlerState.Standing; 
	}
	
	public void setState(Player player, BowlerState bowlerState, double time)
    {
	    stateByPlayer.put(player, bowlerState);
	    setStateChangeTime(player, time);
    }
	
	public BowlerRenderer getRenderer()
    {
    	return renderer;
    }

	public void setRenderer(BowlerRenderer renderer)
    {
    	this.renderer = renderer;
    } 
}
