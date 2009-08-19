package com.siebentag.cj.util.math;

import static com.siebentag.cj.util.math.Constants.*;

public class InstantaneousTrajectory
{
	protected double vx,vy,vz;
	
	public InstantaneousTrajectory(double velocity, Angle angle, Angle elevation)
	{
		double vh = velocity * Math.cos(elevation.radians());
		vz = velocity * Math.sin(elevation.radians());
		vx = vh * Math.sin(angle.radians());
		vy = vh * Math.cos(angle.radians());
	}
	
	public void accelerate(double acceleration)
	{
		double velocity = getVelocity();
		double effective = acceleration * RESOLUTION;
		double percentage = effective / velocity;
		scale(percentage);
	}
	
	private void scale(double percentage)
	{
		vx *= (1.0 + percentage);
		vy *= (1.0 + percentage);
		vz *= (1.0 + percentage);
	}
	
	public double getVelocity()
	{
		return Math.sqrt(vx*vx + vy*vy + vz*vz);
	}
	
	public double getVerticalVelocity()
	{
		return vz;
	}
	
	public double getHorizontalVelocity()
	{
		return Math.sqrt(vx*vx + vy*vy);
	}

	public void applyGravity()
	{
		vz = vz + GRAVITY * RESOLUTION;
	}
	
	public double getDeltaX()
	{
		return vx * RESOLUTION;
	}
	
	public double getDeltaY()
	{
		return vy * RESOLUTION;
	}
	
	public double getDeltaZ()
	{
		return vz * RESOLUTION;
	}
}