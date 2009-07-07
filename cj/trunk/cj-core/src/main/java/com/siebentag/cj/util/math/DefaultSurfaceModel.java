package com.siebentag.cj.util.math;

import org.springframework.stereotype.Component;

@Component("defaultSurfaceModel")
public class DefaultSurfaceModel extends BasicSurfaceModel
{
	public DefaultSurfaceModel()
	{
	}

	@Override
    public boolean containsPoint(Point3D loc)
    {
	    return true;
    }

	@Override
    public double getBounceEfficiency()
    {
	    return 0.1;
    }
	
	@Override
	public boolean inPlay()
	{
	    return false;
	}
}
