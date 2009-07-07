package com.siebentag.cj.graphics;

import java.awt.geom.Point2D;

import com.siebentag.cj.util.math.Point3D;

public class Perspective extends AbstractPointTranslator
{
	public Point2D convert(Point3D point)
	{
//        double py = point.getY();
//        double px = point.getX();
//        double pz = point.getZ();
//        
//        double oy = (py - yMin) / (yMax - yMin);
//
//        double scaley = 0.7;
//        double scalex = (lowerSkew - upperSkew) * oy + upperSkew;
//        
//        double newX = px * scalex;
//        double newY = (py * scaley) - (pz * scalex);
//        
//        double scale = 2 * (double)xOrigin / (100.0 * lowerSkew);
//        
//        return new Point2D.Double(scale * newX + xOrigin, scale * newY + yOrigin);
		
		return null;
	}

	public Point3D convert(Point2D point)
	{
		return null;
	}
}
