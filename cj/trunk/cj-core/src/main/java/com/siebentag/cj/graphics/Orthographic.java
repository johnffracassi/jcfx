package com.siebentag.cj.graphics;

import java.awt.geom.Point2D;

import org.springframework.stereotype.Component;

import com.siebentag.cj.util.math.Point3D;

@Component("orthographicPointTranslator")
public class Orthographic extends AbstractPointTranslator
{
	private double scalex = 1.0;
	private double zoomFactor = 2.0;
	
	public Point2D convert(Point3D point)
	{
		if(point == null)
		{
			return new Point2D.Double(0,0);
		}
		
        double y3 = point.getY();
        double x3 = point.getX();
        double z3 = point.getZ();
        
        double scale = getZoom() * zoomFactor * (double)(getWidth() / 2) / (100.0 * getLowerSkew());
        double scaley = getYRot() / 90.0;
        
        double x2 = scale * (x3 * scalex) + (getWidth() / 2);
        double y2 = scale * (-(y3 * scaley) - (z3 * scalex)) + (getHeight() / 2);
        
		Point2D p2d = new Point2D.Double(x2, y2);

        return p2d;
	}

	public Point3D convert(Point2D point)
	{
		if(point == null)
		{
			return new Point3D(0,0,0);
		}
		
        double scale = getZoom() * zoomFactor * (double)(getWidth() / 2) / (100.0 * getLowerSkew());
        double scaley = (90.0 / getYRot());

        double x2 = point.getX();
		double y2 = point.getY();
		
		double x3 = (x2 - getWidth() / 2) / (scale * scalex);
		double y3 = (y2 - getHeight() / 2) / (-scale * scaley);

		return new Point3D(x3, y3, 0.0);
	}
    
//	private Point3D rotate(Point3D p, double degrees)
//	{
//	  	double distance = p.floored().distanceTo(Point3D.ORIGIN);
//	  	double angle = Math.atan(p.getX() / p.getY());
//	  	double angleInDeg = Math.toDegrees(angle);
//	  	double newAngle = angleInDeg + degrees;
//	  	double newX = distance * Math.sin(Math.toRadians(newAngle));
//	  	double newY = distance * Math.cos(Math.toRadians(newAngle));
//	  	return new Point3D(newX, newY, p.getZ());
//	}
}
