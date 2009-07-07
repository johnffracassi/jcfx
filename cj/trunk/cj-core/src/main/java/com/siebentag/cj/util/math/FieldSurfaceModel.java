package com.siebentag.cj.util.math;

import java.awt.Polygon;

public class FieldSurfaceModel extends BasicSurfaceModel
{
	public FieldSurfaceModel()
	{
		Polygon polygon = new Polygon();
		polygon.addPoint(-60, 0);
		polygon.addPoint(0, 60);
		polygon.addPoint(60, 0);
		polygon.addPoint(0, -60);
		polygon.addPoint(-60, 0);
		setPolygon(polygon);
	}
}
