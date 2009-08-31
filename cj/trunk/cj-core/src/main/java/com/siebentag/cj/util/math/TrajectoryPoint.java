package com.siebentag.cj.util.math;



@SuppressWarnings("serial")
public class TrajectoryPoint extends Point3D
{
	private Time time;
	private boolean hasBounced;

	/**
     * @param loc
     * @param t
     */
    public TrajectoryPoint(Point3D loc, Time time)
    {
    	super(loc);
    	this.time = time;
    }

    @Override
    public String toString() {
    	return String.format("%s@%.2f", super.toString(), time.getTime());
    }
    
	public Time getTime()
	{
		return time;
	}

	public void setTime(Time time)
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
