package com.siebentag.cj.util.math;

import static com.siebentag.cj.util.math.Constants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrajectoryManager
{
	/** the slowest velocity the ball can have before it is considered stopped */
	private double ballStopVelocity 	= 0.1;
	private double gravity 				= -9.8;
	private double ballMass 			= 0.156;
	private double keeperTakeHeight     = 0.7;

	/** number of key frames per second */
	private int    pointsPerSecond 	= 30;
	
	@Autowired
	private SurfaceManager surfaceManager;

	public TrajectoryPath calculate(TrajectoryModel model)
	{
		TrajectoryPath trajectoryPath = new TrajectoryPath(getResolution());
		Point3D loc = model.getOrigin();
			
		// initial instantaneous heading and elevation
		double direction = model.getAngle();
		double elevation = model.getElevation();
		
		// initial instantaneous velocities
		InstantaneousTrajectory trajectory = new InstantaneousTrajectory(model.getVelocity(), direction, elevation);

		// time counter
		double t = 0.0;
		int bounceCount = 0;
		while(trajectory.getVelocity() > getBallStopVelocity() && t < 25.0)
		{
			t += getResolution();
			
			// gravity
			trajectory.applyGravity();

			// TODO jc - apply wind to trajectory
			
			// what are we on top of/bouncing on?
			SurfaceModel surface = surfaceManager.getSurface(loc);
			
			// check for bounce
			if(requiresBounce(loc))
			{
				if(bounceCount == 0)
				{
					trajectoryPath.setFirstBounceTime(t);
				}
				
				bounceCount ++;

				// invert z velocity and multiply by bounce eff
				applyBounce(trajectory, surface.getBounceEfficiency());
			}

			// determine new location
			double dx = trajectory.getDeltaX();
			double dy = trajectory.getDeltaY();
			double dz = trajectory.getDeltaZ();			
		
			// find the new location and add it to the path 
			loc = loc.offset(dx, dy, dz);
			TrajectoryPoint pt = new TrajectoryPoint(loc, t);
			pt.setHasBounced(bounceCount > 0);
			trajectoryPath.addPoint(pt);

			// end the trajectory after 10 bounces (should've lost all energy or disappeared off screen by then)
			if(bounceCount > 10)
			{
				break;
			}
		}
		
		return trajectoryPath;
	}
	
	/**
	 * Slow the ball down after it hits the ground 
	 */
	private void applyBounce(InstantaneousTrajectory trajectory, double surfaceBounceEfficiency)
	{
		double angleOfIncidenceV = Math.atan(Math.abs(trajectory.getVerticalVelocity()) / Math.abs(trajectory.getHorizontalVelocity()));
		double angleOfIncidenceH = Math.toRadians(90.0) - Math.atan(Math.abs(trajectory.getVerticalVelocity()) / Math.abs(trajectory.getHorizontalVelocity()));
		
		double efficiency = getBounceEfficiency(angleOfIncidenceV, angleOfIncidenceH);

		double velocity = trajectory.getVelocity();
		double energy = 0.5 * BALL_MASS * velocity * velocity;
		energy *= efficiency;
		double newVelocity = Math.sqrt(2 * energy / BALL_MASS);
		
		double scale = newVelocity / velocity;
//		trajectory.vx *= scale;
//		trajectory.vy *= scale;
		trajectory.vz = Math.abs(trajectory.vz * scale);
	}
		
	private boolean requiresBounce(Point3D loc)
	{
		return loc.getZ() <= 0.0;
	}
	
	/**
	 * Estimate a trajectory from one point to another
	 * 
	 * @param fielder
	 * @param origin
	 * @param target
	 * @return
	 */
    public TrajectoryPath getApproximateSimpleTrajectory(double maxInitialVelocity, Point3D origin, Point3D target)
	{
		double dx = target.getX() - origin.getX();
		double dy = target.getY() - origin.getY();
		double d = Math.sqrt(dx*dx + dy*dy);

		// TODO use a variable speed for the return throw
		double flightTime = d / 17.5;
		double uz = 9.8 * flightTime / 2;
		
		int pointCount = (int)(flightTime / getResolution()) + 1;
		
		Point3D[] points = new Point3D[pointCount];
		
		for(int i=0; i<pointCount; i++)
		{
			double time = i * getResolution();
			points[i] = new Point3D(origin.getX() + time / flightTime * dx, origin.getY() + time / flightTime * dy, keeperTakeHeight + uz * time + 0.5 * -gravity * time*time);
		}
		
		TrajectoryPath path = new TrajectoryPath(getResolution(), points);
		
		return path;
	}
	
	/**
	 * Slightly adjust the efficiency based on how steep the ball
	 * is hitting the ground
	 */
	private double getBounceEfficiency(double angleOfIncidenceV, double angleOfIncidenceH)
	{
		double aoiDegrees = Math.toDegrees(angleOfIncidenceV);
		
		double eff = 0.15 + (((90.0 - aoiDegrees) / 90.0) * 0.85);
		
		return eff;
	}
	
	public double getBallStopVelocity()
    {
    	return ballStopVelocity;
    }

	public void setBallStopVelocity(double ballStopVelocity)
    {
    	this.ballStopVelocity = ballStopVelocity;
    }

	public double getGravity()
    {
    	return gravity;
    }

	public void setGravity(double gravity)
    {
    	this.gravity = gravity;
    }

	public double getBallMass()
    {
    	return ballMass;
    }

	public void setBallMass(double ballMass)
    {
    	this.ballMass = ballMass;
    }

	public int getPointsPerSecond()
    {
    	return pointsPerSecond;
    }

	public void setPointsPerSecond(int pointsPerSecond)
    {
    	this.pointsPerSecond = pointsPerSecond;
    }

	public double getResolution()
    {
    	return Constants.RESOLUTION;
    }

	public double getKeeperTakeHeight()
    {
    	return keeperTakeHeight;
    }

	public void setKeeperTakeHeight(double keeperTakeHeight)
    {
    	this.keeperTakeHeight = keeperTakeHeight;
    }

	public SurfaceManager getSurfaceManager()
    {
    	return surfaceManager;
    }

	public void setSurfaceManager(SurfaceManager surfaceManager)
    {
    	this.surfaceManager = surfaceManager;
    }
}

