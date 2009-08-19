package com.siebentag.cj.game;

import com.siebentag.cj.util.math.Angle;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.TrajectoryModel;


public class BowlModel extends TrajectoryModel implements Cloneable
{
	private SwingType swingType = SwingType.None;
	private SpinType spinType   = SpinType.None;
	private double swingAmount  = 0.0; // measured in radians per second
	private Angle spinAmount    = Angle.ZERO;
	private double bounce       = 1.0; // bounce efficiency
		
	public BowlModel(Point3D origin, double speed, Angle angle, Angle elevation, SwingType swingType, double swing, SpinType spinType, Angle spin, double bounce)
	{
		this(origin, speed, angle, elevation);
		this.swingType = swingType;
		this.swingAmount = swing;
		this.spinType = spinType;
		this.spinAmount = spin;
		this.bounce = bounce;
	}
	
	public BowlModel(Point3D point3D, double speed, Angle angle, Angle elevation)
	{
		super(point3D, speed, angle, elevation);
	}

	public double getBounce()
	{
		return bounce;
	}

	public void setBounce(double bounce)
	{
		this.bounce = bounce;
	}

	public Angle getSpinAmount()
	{
		return spinAmount;
	}

	public void setSpinAmount(Angle spinAmount)
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
