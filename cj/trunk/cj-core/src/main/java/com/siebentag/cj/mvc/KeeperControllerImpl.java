package com.siebentag.cj.mvc;

import java.awt.Graphics2D;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.field.FieldPosition;
import com.siebentag.cj.graphics.renderer.FielderRenderer;
import com.siebentag.cj.model.Player;
import com.siebentag.cj.model.Team;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.TrajectoryPath;

@Component
public class KeeperControllerImpl extends PlayerControllerImpl implements KeeperController
{
    private static final Logger log = Logger.getLogger(KeeperControllerImpl.class);

	@Autowired
	private FielderRenderer renderer;

	private Player keeper;
	private FielderState state;
	private double timeOfLastStateChange;
	private FieldPosition fieldPosition;
	
	public void paint(Graphics2D g, double time)
    {
		Point3D sLoc = getLocation(keeper, time);
		renderer.render(g, sLoc, getState(), time - timeOfLastStateChange);
    }

	public void resetForInnings(Team battingTeam, Team fieldingTeam)
	{
		assignKeeper(fieldingTeam.getPlayers().getPlayers().get(10));
		
		// TODO set team colours on the renderer

		resetForBall();
	}

	public void resetForBall()
    {
		timeOfLastStateChange = 0.0;
		fieldPosition = FieldPosition.KeeperFast;
		state = FielderState.Idle;
		setLocation(keeper, fieldPosition.getLocation());
    }

	public void assignKeeper(Player player)
	{
		this.keeper = player;
	}

	public Player getKeeper()
	{
		return keeper;
	}

	public FielderState getState()
	{
		return state == null ? FielderState.Idle : state;
	}
	
	public void setState(FielderState state, double time)
	{
		this.state = state;
		this.timeOfLastStateChange = time;
	}
	
	public void moveToStumps(double time)
	{
		PersonMovement movement = new PersonMovement();
		movement.setSource(getLocation(keeper, time));
		movement.setDestination(FieldPosition.KeeperReturn.getLocation());
		movement.setMoveStyle(MoveStyle.Run);
		movement.setStartTime(time);
		movement.setPerson(keeper);

		doMove(movement);
	}
	
	public void moveToLineOfBall(TrajectoryPath path, double ballTimeAtBatsman)
	{
		log.debug("move keeper to line of ball (start=" + ballTimeAtBatsman + ")");
		
		Point3D target = path.getLocationAtY(fieldPosition.getLocation().getY());
		
		PersonMovement movement = new PersonMovement();
		movement.setSource(getLocation(keeper, ballTimeAtBatsman));
		movement.setDestination(target);
		movement.setMoveStyle(MoveStyle.Run);
		movement.setStartTime(ballTimeAtBatsman);
		movement.setPerson(keeper);

		doMove(movement);
	}

	public FieldPosition getFieldPosition()
    {
    	return fieldPosition;
    }

	public void setFieldPosition(FieldPosition fieldPosition)
    {
    	this.fieldPosition = fieldPosition;
    }
}
