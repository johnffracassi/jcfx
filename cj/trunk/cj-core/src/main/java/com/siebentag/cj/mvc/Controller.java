package com.siebentag.cj.mvc;

import java.awt.Graphics2D;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.action.PersonRole;
import com.siebentag.cj.game.event.BallStartedEvent;
import com.siebentag.cj.game.event.InningsCompleteEvent;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.util.math.Time;

@Component
public class Controller implements EventListener
{
	@Autowired
	BatsmanController batsmanController;

	@Autowired
	BowlerController bowlerController;

	@Autowired
	FielderController fielderController;

	@Autowired
	KeeperController keeperController;

	@Autowired
	UmpireController umpireController;
	
	@Autowired
	BallControllerImpl ballController;

	public void paint(Graphics2D g, Time time)
	{
		umpireController.paint(g, time);
		ballController.paint(g, time);
		batsmanController.paint(g, time);
		bowlerController.paint(g, time);
		keeperController.paint(g, time);
		fielderController.paint(g, time); // deal with fielders last
	}
	
	public void resetForBall()
	{
		umpireController.resetForBall();
		ballController.resetForBall();
		batsmanController.resetForBall();
		bowlerController.resetForBall();
		keeperController.resetForBall();
		fielderController.resetForBall(); // deal with fielders last
	}
	
	public void resetForInnings(Team battingTeam, Team fieldingTeam)
	{
		umpireController.resetForInnings(battingTeam, fieldingTeam);
		batsmanController.resetForInnings(battingTeam, fieldingTeam);
		bowlerController.resetForInnings(battingTeam, fieldingTeam);
		keeperController.resetForInnings(battingTeam, fieldingTeam);
		fielderController.resetForInnings(battingTeam, fieldingTeam); // deal with fielders last
	}
	
	public PersonController getControllerByRole(PersonRole role)
	{
		if(role == PersonRole.Fielder)
		{
			return getFielderController();
		}
		else if(role == PersonRole.Batsman)
		{
			return getBatsmanController();
		}
		else if(role == PersonRole.Bowler)
		{
			return getBowlerController();
		}
		else if(role == PersonRole.Keeper)
		{
			return getKeeperController();
		}
		else if(role == PersonRole.Umpire)
		{
			return getUmpireController();
		}
		
		throw new UnsupportedOperationException(role + " does not have a controller");
	}
	
	public BatsmanController getBatsmanController()
	{
		return batsmanController;
	}

	public void setBatsmanController(BatsmanController batsmanController)
	{
		this.batsmanController = batsmanController;
	}

	public BowlerController getBowlerController()
	{
		return bowlerController;
	}

	public void setBowlerController(BowlerController bowlerController)
	{
		this.bowlerController = bowlerController;
	}

	public FielderController getFielderController()
	{
		return fielderController;
	}

	public void setFielderController(FielderController fielderController)
	{
		this.fielderController = fielderController;
	}

	public KeeperController getKeeperController()
	{
		return keeperController;
	}

	public void setKeeperController(KeeperController keeperController)
	{
		this.keeperController = keeperController;
	}

	public UmpireController getUmpireController()
	{
		return umpireController;
	}

	public void setUmpireController(UmpireController umpireController)
	{
		this.umpireController = umpireController;
	}

	public void event(Event event)
    {
		if(event instanceof BallStartedEvent)
		{
			resetForBall();
		}
    }

	public Class<?>[] register()
    {
	    return new Class<?>[] {
	    	BallStartedEvent.class, InningsCompleteEvent.class
	    };
    }
}
