package com.siebentag.cj.graphics;

import java.awt.Polygon;
import java.util.LinkedList;

import com.siebentag.cj.util.math.Point3D;

public class Polygon3D extends LinkedList<Point3D> 
{
	private static final long serialVersionUID = -6678863045310745846L;
	
	Polygon flat;
	
	public Polygon3D()
	{
		flat = new Polygon();
	}
	
	public Polygon getFlat()
	{
		return flat;
	}
	
	@Override public boolean add(Point3D point) 
	{
		flat.addPoint((int)point.getX(), (int)point.getY());
		return super.add(point);
	}
}
