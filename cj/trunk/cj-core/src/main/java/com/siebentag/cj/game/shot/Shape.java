package com.siebentag.cj.game.shot;

import java.awt.Polygon;

import org.apache.log4j.Logger;

public class Shape
{
	private static final Logger log = Logger.getLogger("Shots");

	private Polygon polygon;
	private Polygon bounds;
	
	double scale = 200.0;
	
	public Shape()
	{
		log.debug("Shape()");
		polygon = new Polygon();
		bounds = new Polygon();
	}
	
	public void addPoint(String str)
	{
		String[] split = str.split(",");
		
		double x = Double.parseDouble(split[0]);
		double y = Double.parseDouble(split[1]);
		
		polygon.addPoint((int)(scale + x * scale), (int)(2 * scale - y * scale));
		bounds.addPoint((int)(1000.0 * x), (int)(1000.0 * y));

		log.debug("Shape.addPoint(" + x + "," + y + ")");
	}
	
	public boolean contains(double x, double y)
	{
		return (bounds.contains(1000.0 * x, 1000.0 * y));
	}
	
	public Polygon getPolygon()
	{
		return polygon;
	}
}

