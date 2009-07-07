package com.siebentag.cj.graphics;

import org.apache.log4j.Logger;


public abstract class AbstractPointTranslator implements PointTranslator
{
	private static final Logger log = Logger.getLogger(AbstractPointTranslator.class);

	private int width;
	private int height;
	private double yRot = 30.0;
	private double zRot = 0.0;
	private double lowerSkew = 1.4;
	private double upperSkew = 0.6;
	private double zoom = 1.0;

	public void setSkew(double lower, double upper)
	{
		this.lowerSkew = lower;
		this.upperSkew = upper;
	}
	
	public void setDimensions(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		log.debug("setDimensions - " + width + "," + height);
	}
	
	public void setRotation(double yRotation, double zRotation)
	{
		this.yRot = yRotation;
		this.zRot = zRotation;
	}
	
	public void setZoom(double zoom)
	{
		this.zoom = zoom;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public double getYRot()
	{
		return yRot;
	}

	public double getZRot()
	{
		return zRot;
	}

	public double getLowerSkew()
	{
		return lowerSkew;
	}

	public double getUpperSkew()
	{
		return upperSkew;
	}

	public double getZoom()
	{
		return zoom;
	}
}
