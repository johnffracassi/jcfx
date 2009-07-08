package com.siebentag.cj.util.math;

import java.awt.Polygon;


public class BasicSurfaceModel implements SurfaceModel
{
	private Polygon polygon;
    private double bounceEfficiency;
    private int zOrder;

    public BasicSurfaceModel()
    {
    	polygon = null;
    	bounceEfficiency = 0.0;
    	zOrder = 0;
    }
    
    public boolean containsPoint(Point3D loc)
    {
    	return getPolygon().contains(loc.getX(), loc.getY());
    }

	public int compareTo(SurfaceModel o)
    {
	    return zOrder > o.getZOrder() ? -1 : (zOrder < o.getZOrder() ? 1 : 0);
    }

	public double getBounceEfficiency()
    {
    	return bounceEfficiency;
    }

	public void setBounceEfficiency(double bounceEfficiency)
    {
    	this.bounceEfficiency = bounceEfficiency;
    }

	public int getZOrder()
    {
    	return zOrder;
    }

	public void setZOrder(int order)
    {
    	zOrder = order;
    }

	public Polygon getPolygon()
    {
    	return polygon;
    }

	public void setPolygon(Polygon polygon)
    {
    	this.polygon = polygon;
    }

	public boolean inPlay()
    {
	    return true;
    }
}
