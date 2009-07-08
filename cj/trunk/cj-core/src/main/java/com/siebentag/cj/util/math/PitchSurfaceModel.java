package com.siebentag.cj.util.math;

import java.awt.Polygon;

import org.springframework.stereotype.Component;

@Component
public class PitchSurfaceModel extends BasicSurfaceModel
{
	public PitchSurfaceModel()
	{
		Polygon polygon = new Polygon();
		polygon.addPoint(-1, -10);
		polygon.addPoint(1, -10);
		polygon.addPoint(1, 10);
		polygon.addPoint(-1, 10);
		setPolygon(polygon);
	}
}
