package com.siebentag.cj.util.math;


public class TrajectoryModel
{
	private Point3D origin;
	private double initialVelocity;
	
	private Angle angle;
	private Angle elevation;
	
	private double windSpeed = 0.0;
	private Angle windDirection = Angle.degrees(0);
	
	public TrajectoryModel()
	{
		this(0, Angle.degrees(0), Angle.degrees(0));
	}
	
	public TrajectoryModel(Point3D origin, double speed, Angle angle, Angle elevation)
	{
		this.origin 		 = origin;
		this.initialVelocity = speed;
		this.angle 			 = angle;
		this.elevation 		 = elevation;
	}

	public String toString()
	{
		return "(" + origin + ", v=" + initialVelocity + "m/s, a=" + angle.degrees() + ", e=" + elevation.degrees() + ", wind=" + windSpeed + "m/s@" + windDirection + ")";
	}
	
	public TrajectoryModel(double speed, Angle angle, Angle elevation)
	{
		this(Point3D.ORIGIN, speed, angle, elevation);
	}

	/**
	 * 
	 * @return the angle (in radians)
	 */
	public Angle getAngle()
	{
		return angle;
	}

	/**
	 * 
	 * @param radians The angle (in radians)
	 */
	public void setAngle(Angle radians)
	{
		this.angle = radians;
	}

	public Angle getElevation()
	{
		return elevation;
	}

	public void setElevation(Angle elevation)
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

	public Angle getWindDirection()
	{
		return windDirection;
	}

	public void setWindDirection(Angle windDirection)
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
