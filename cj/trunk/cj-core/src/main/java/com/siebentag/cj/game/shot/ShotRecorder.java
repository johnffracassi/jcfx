package com.siebentag.cj.game.shot;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ShotRecorder
{
	private static final Logger log = Logger.getLogger(ShotRecorder.class);

	private List<Point2D> points;

	private ShotRecorder()
	{
		points = Collections.synchronizedList(new ArrayList<Point2D>(100));
	}
	
	public void reset()
	{
		log.debug("Reset shot recorder");
		getShotPoints().clear();
	}
	
	public void addPoint(Point2D point)
	{
		log.trace("Adding shot point " + point);
		getShotPoints().add(point);
	}
	
    public List<Point2D> getShotPoints()
    {
	    return points;
    }
}
