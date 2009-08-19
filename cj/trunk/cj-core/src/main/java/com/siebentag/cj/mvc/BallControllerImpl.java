package com.siebentag.cj.mvc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.event.BallPickedUpEvent;
import com.siebentag.cj.graphics.World;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.TrajectoryPath;

@Component
public class BallControllerImpl implements BallController, EventListener
{
	private static final Logger log = Logger.getLogger(BallControllerImpl.class);

	@Autowired
	private World world;
	
	private TrajectoryPath trajectoryPath;
	private double trajectoryStartTime;
	private BallState ballState = BallState.None;
	
	private Color ballColour = Color.RED;
	private Color shadowColour = new Color(0.0f, 0.0f, 0.0f, 0.5f);
	
	public void paint(Graphics2D g, double time)
    {
		if(getBallState() == BallState.InFlight)
		{
			Point3D loc3d = getLocation(time);
			Point3D shadowLoc3d = getLocation(time).floored();
			
			Point2D loc2d = world.convert(loc3d);
			Point2D shadowLoc2d = world.convert(shadowLoc3d);

			// shadow
			g.setColor(shadowColour);
			g.drawRect((int)shadowLoc2d.getX(), (int)shadowLoc2d.getY(), 1, 1);

			// ball
			g.setColor(ballColour);
			g.drawRect((int)loc2d.getX(), (int)loc2d.getY(), 1, 1);
		}
    }
	
	public void resetForBall()
    {
		setBallState(BallState.None);
		trajectoryPath = null;
		trajectoryStartTime = 0.0;
    }

	public Point3D getLocation(double time)
    {
		if(trajectoryPath != null)
		{
			double trajectoryTime = time - trajectoryStartTime;
			return trajectoryPath.getLocation(trajectoryTime);
		}
		else
		{
			return Point3D.ORIGIN;
		}
    }

	public void setTrajectoryPath(TrajectoryPath trajectoryPath, double time)
	{
		log.debug("new trajectory set for ball (" + trajectoryPath.getPoints().size() + ")");
		
		this.trajectoryPath = trajectoryPath;
		this.trajectoryStartTime = time;
		setBallState(BallState.InFlight);
	}
	
	public TrajectoryPath getTrajectoryPath()
    {
	    return trajectoryPath;
    }

	public double getTrajectoryStartTime()
    {
    	return trajectoryStartTime;
    }

	public BallState getBallState()
    {
    	return ballState;
    }

	public void setBallState(BallState ballState)
    {
    	this.ballState = ballState;
    }

	public void event(Event event)
    {
		if(event instanceof BallPickedUpEvent)
		{
			setBallState(BallState.InHand);
		}
    }

	public Class<?>[] register()
    {
	    return new Class<?>[] { BallPickedUpEvent.class };
    }
}
