package com.siebentag.cj.graphics;

import java.awt.geom.Point2D;

import com.siebentag.cj.util.math.Point3D;

public interface PointTranslator
{
	void setDimensions(int width, int height);
	void setRotation(double yRotation, double zRotation);
	void setSkew(double lower, double upper);
	void setZoom(double zoom);
	Point2D convert(Point3D pt);
	Point3D convert(Point2D pt);
}
