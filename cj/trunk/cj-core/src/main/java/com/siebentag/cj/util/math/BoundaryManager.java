package com.siebentag.cj.util.math;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.model.BoundaryType;

@Component
public class BoundaryManager
{
    private static final Logger log = Logger.getLogger(BoundaryManager.class);

	@Autowired
	private SurfaceManager surfaceManager;

	public BoundaryIntersection getBoundaryIntersection(TrajectoryPath path)
	{
		log.debug("find intersection between path and boundary");
		
		BoundaryIntersection intersection = new BoundaryIntersection();
		
		for(TrajectoryPoint pt : path.getPoints())
		{
			if(!surfaceManager.getSurface(pt).inPlay())
			{
				intersection.setLocation(pt);

				if(pt.getTime().isAfter(path.getFirstBounceTime())) 
				{
					intersection.setType(BoundaryType.SIX);
				}
				else
				{
					intersection.setType(BoundaryType.FOUR);
				}

				log.debug(String.format("boundary intersection found at %s (%s). bounceTime=%.1f / pointTime=%.1f", pt, intersection.getType(), path.getFirstBounceTime().getTime(), pt.getTime().getTime()));
				
				return intersection;
			}
		}
		
		return intersection;
	}
	
	public SurfaceManager getSurfaceManager()
    {
    	return surfaceManager;
    }

	public void setSurfaceManager(SurfaceManager surfaceManager)
    {
    	this.surfaceManager = surfaceManager;
    }
}
