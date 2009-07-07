/*
 * World3d.java
 * 
 * Created on 24-Aug-2007, 14:03:47
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.siebentag.cj.graphics;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.siebentag.cj.util.math.Point3D;

@Component
public class World implements PointTranslator
{
    private int height = 0;
    private int width = 0;
   
    @Autowired
    @Qualifier("orthographicPointTranslator")
    private PointTranslator translator;
    
    @Autowired
    private List<WorldChangeListener> listeners;
    
    public World() 
    {
    }

    public void setListeners(List<WorldChangeListener> listeners)
    {
    	this.listeners = listeners;
    }
    
    public List<WorldChangeListener> getListeners()
    {
    	return listeners;
    }
    
    public void notifyListeners()
    {
    	for(WorldChangeListener listener : listeners)
    	{
    		listener.worldChanged();
    	}
    }
    
    public Point2D convert(double x, double y, double z)
    {
    	return convert(new Point3D(x, y, z));
    }
    
    public Point2D convert(double x, double y)
    {
    	return convert(x, y, 0.0);
    }
    
    public Polygon convert(Polygon3D polygon)
    {
    	Polygon p = new Polygon();
    	
    	for(Point3D point : polygon)
    	{
    		Point2D v2d = convert(point);
    		p.addPoint((int)v2d.getX(), (int)v2d.getY());
    	}
    	
    	return p;
    }
    
	public PointTranslator getTranslator()
	{
		return translator;
	}

	public void setTranslator(PointTranslator translator)
	{
		this.translator = translator;
	}

	////////////////////////////////////////////////////////////////////////
    // pass through methods
    ////////////////////////////////////////////////////////////////////////
    
    public Point2D convert(Point3D point)
    {
    	return translator.convert(point);
    }
     
	public Point3D convert(Point2D point)
	{
		return translator.convert(point);
	}

	public void setSkew(double lower, double upper)
	{
		translator.setSkew(lower, upper);
    	notifyListeners();
	}

	public void setZoom(double zoom)
	{
		translator.setZoom(zoom);
    	notifyListeners();
	}
	
	public void setRotation(double yRotation, double zRotation)
    {
    	translator.setRotation(yRotation, zRotation);
    	notifyListeners();
    }
    
	public void setDimensions(int width, int height)
	{
		if(this.width != width || this.height != height)
		{
			this.height = height;
			this.width = width;

			translator.setDimensions(width, height);
			
			notifyListeners();
		}
	}

	public int getHeight()
    {
    	return height;
    }

	public void setHeight(int height)
    {
    	this.height = height;
    }

	public int getWidth()
    {
    	return width;
    }

	public void setWidth(int width)
    {
    	this.width = width;
    }
}
