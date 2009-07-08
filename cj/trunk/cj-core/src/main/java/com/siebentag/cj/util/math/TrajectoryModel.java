package com.siebentag.cj.util.math;

import static java.lang.Math.*;

public class TrajectoryModel
{
	private Point3D origin;
	private double initialVelocity;
	
	/**
	 * The angle in radians
	 */
	private double angle;
	
	private double elevation;
	
	private double windSpeed = 0.0;
	private double windDirection = 0.0;
	
	public TrajectoryModel()
	{
		this(0,0,0);
	}
	
	public TrajectoryModel(Point3D origin, double speed, double radians, double elevation)
	{
		this.origin 		 = origin;
		this.initialVelocity = speed;
		this.angle 			 = radians;
		this.elevation 		 = elevation;
	}

	public String toString()
	{
		return "(" + origin + ", v=" + initialVelocity + "m/s, a=" + toDegrees(angle) + ", e=" + toDegrees(elevation) + ", wind=" + windSpeed + "m/s@" + windDirection + ")";
	}
	
	public TrajectoryModel(double speed, double radians, double elevation)
	{
		this(Point3D.ORIGIN, speed, radians, elevation);
	}

	/**
	 * 
	 * @return the angle (in radians)
	 */
	public double getAngle()
	{
		return angle;
	}

	/**
	 * 
	 * @param radians The angle (in radians)
	 */
	public void setAngle(double radians)
	{
		this.angle = radians;
	}

	public double getElevation()
	{
		return elevation;
	}

	public void setElevation(double elevation)
	{
		this.elevation = elevation;
	}

	public Point3D getOrigin()
	{
		return origin;
	}

	public void setOrigin(Point3D origin)
	{
		this.origin = origin;
	}

	public double getVelocity()
	{
		return initialVelocity;
	}

	public void setVelocity(double velocity)
	{
		this.initialVelocity = velocity;
	}

	public double getWindDirection()
	{
		return windDirection;
	}

	public void setWindDirection(double windDirection)
	{
		this.windDirection = windDirection;
	}

	public double getWindSpeed()
	{
		return windSpeed;
	}

	public double getWindSpeed(VectorComponent component)
	{
		return Calculator.getComponentValue(getWindSpeed(), getWindDirection(), component);
	}
	
	public void setWindSpeed(double windSpeed)
	{
		this.windSpeed = windSpeed;
	}
	
	public double getVelocity(VectorComponent component)
	{
		return Calculator.getComponentValue(getVelocity(), getAngle(), getElevation(), component);
	}
}
