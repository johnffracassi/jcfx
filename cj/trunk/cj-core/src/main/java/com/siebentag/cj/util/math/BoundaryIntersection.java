package com.siebentag.cj.util.math;

import com.siebentag.cj.model.BoundaryType;

public class BoundaryIntersection
{
	private TrajectoryPoint loc;
	private BoundaryType type;

	public BoundaryIntersection()
	{
		loc = null;
		type = BoundaryType.NONE;
	}
	
	public TrajectoryPoint getLocation()
	{
		return loc;
	}

	public void setLocation(TrajectoryPoint loc)
	{
		this.loc = loc;
	}

	public BoundaryType getType()
	{
		return type;
	}

	public void setType(BoundaryType type)
	{
		this.type = type;
	}
}
