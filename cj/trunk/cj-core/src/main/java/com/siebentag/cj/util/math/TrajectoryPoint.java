package com.siebentag.cj.util.math;


@SuppressWarnings("serial")
public class TrajectoryPoint extends Point3D
{
	private double time;
	private boolean hasBounced;

	/**
     * @param loc
     * @param t
     */
    public TrajectoryPoint(Point3D loc, double t)
    {
    	super(loc);
    	this.time = t;
    }

	public double getTime()
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}

	public boolean hasBounced()
    {
    	return hasBounced;
    }

	public void setHasBounced(boolean hasBounced)
    {
    	this.hasBounced = hasBounced;
    }
}
