package com.siebentag.cj.util.math;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class SurfaceManager
{
	@Autowired
	private List<SurfaceModel> surfaces;
	
	@Autowired
	@Qualifier("defaultSurfaceModel")
	BasicSurfaceModel defaultSurface;

	public List<SurfaceModel> getSurfaces()
    {
    	return surfaces;
    }

	public void setSurfaces(List<SurfaceModel> surfaces)
    {
    	this.surfaces = surfaces;
    	Collections.sort(surfaces);
    }
	
	public SurfaceModel getSurface(Point3D loc)
	{
		for(SurfaceModel surface : surfaces)
		{
			if(surface.containsPoint(loc))
			{
				return surface;
			}
		}
		
		return defaultSurface;
	}

	public BasicSurfaceModel getDefaultSurface()
    {
    	return defaultSurface;
    }

	public void setDefaultSurface(BasicSurfaceModel defaultSurface)
    {
    	this.defaultSurface = defaultSurface;
    }
}
