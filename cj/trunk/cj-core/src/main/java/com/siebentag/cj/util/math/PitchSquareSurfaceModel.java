package com.siebentag.cj.util.math;

import java.awt.Polygon;

import org.springframework.stereotype.Component;

@Component
public class PitchSquareSurfaceModel extends BasicSurfaceModel
{
	public PitchSquareSurfaceModel()
	{
		Polygon polygon = new Polygon();
		polygon.addPoint(-10, 11);
		polygon.addPoint(10, -11);
		polygon.addPoint(10, 11);
		polygon.addPoint(-10, 11);
		setPolygon(polygon);
	}
}
