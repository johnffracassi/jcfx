package com.siebentag.cj.game;

import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.TrajectoryModel;


public class BowlModel extends TrajectoryModel implements Cloneable
{
	SwingType swingType = SwingType.None;
	SpinType spinType   = SpinType.None;
	double swingAmount  = 0.0; // measured in radians per second
	double spinAmount   = 0.0; // relative angle in radians
	double bounce       = 1.0; // bounce efficiency
		
	public BowlModel(Point3D origin, double speed, double radians, double elevation, SwingType swingType, double swing, SpinType spinType, double spin, double bounce)
	{
		this(origin, speed, radians, elevation);
		this.swingType = swingType;
		this.swingAmount = swing;
		this.spinType = spinType;
		this.spinAmount = spin;
		this.bounce = bounce;
	}
	
	public BowlModel(Point3D point3D, double speed, double radians, double elevation)
	{
		super(point3D, speed, radians, elevation);
	}

	public double getBounce()
	{
		return bounce;
	}

	public void setBounce(double bounce)
	{
		this.bounce = bounce;
	}

	public double getSpinAmount()
	{
		return spinAmount;
	}

	public void setSpinAmount(double spinAmount)
	{
		this.spinAmount = spinAmount;
	}

	public SpinType getSpinType()
	{
		return spinType;
	}

	public void setSpinType(SpinType spinType)
	{
		this.spinType = spinType;
	}

	public double getSwingAmount()
	{
		return swingAmount;
	}

	public void setSwingAmount(double swingAmount)
	{
		this.swingAmount = swingAmount;
	}

	public SwingType getSwingType()
	{
		return swingType;
	}

	public void setSwingType(SwingType swingType)
	{
		this.swingType = swingType;
	}
}
