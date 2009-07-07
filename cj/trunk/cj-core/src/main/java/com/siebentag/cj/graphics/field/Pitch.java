package com.siebentag.cj.graphics.field;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.Polygon3D;
import com.siebentag.cj.graphics.World;
import com.siebentag.cj.graphics.canvas.AbstractCanvasEntity;
import com.siebentag.cj.util.math.Point3D;

@Component
public class Pitch extends AbstractCanvasEntity
{
	@Autowired
	private World world;
	
	private double lengthOverhang = 1.22;
	private double widthOverhang = 0.51;
	private double creaseOverhang = 2.5;
	private double length = 20.12;
	private double width  = 2.64;
	private double hWidth = width / 2.0;
	private double hLength = length / 2.0;
	
	private Color colour  = new Color(240, 240, 200);
	
	private final Point3D[] pts = new Point3D[100];

	public Pitch()
	{
		init();
	}
	
	private void init()
	{
		pts[ 0] = new Point3D(- creaseOverhang, hLength - lengthOverhang);
		pts[ 1] = new Point3D(creaseOverhang, hLength - lengthOverhang);
		pts[ 2] = new Point3D(- creaseOverhang, -hLength + lengthOverhang);
		pts[ 3] = new Point3D(creaseOverhang, -hLength + lengthOverhang);
		
		pts[ 4] = new Point3D(- hWidth, hLength);
		pts[ 5] = new Point3D(hWidth, hLength);
		pts[ 6] = new Point3D(- hWidth, -hLength);
		pts[ 7] = new Point3D(hWidth, -hLength);

		pts[ 8] = new Point3D(- hWidth, -hLength - lengthOverhang);
		pts[ 9] = new Point3D(- hWidth, -hLength + lengthOverhang);
		pts[10] = new Point3D(hWidth, -hLength - lengthOverhang);
		pts[11] = new Point3D(hWidth, -hLength + lengthOverhang);
		pts[12] = new Point3D(- hWidth, hLength + lengthOverhang);
		pts[13] = new Point3D(- hWidth, hLength - lengthOverhang);
		pts[14] = new Point3D(hWidth, hLength + lengthOverhang);
		pts[15] = new Point3D(hWidth, hLength - lengthOverhang);
	}
	
	public int getZOrder()
	{
		return 7;
	}
	
	public void paint(Graphics2D g, double time)
	{
		g.setColor(colour);
		g.drawPolygon(world.convert(getShape()));
		drawMarkings(g);
	}

	private void drawMarkings(Graphics2D g)
	{
		g.setColor(Color.WHITE);
		
		line(g, pts[0], pts[1]); 
		line(g, pts[2], pts[3]); 
		
		line(g, pts[4], pts[5]); 
		line(g, pts[6], pts[7]); 

		line(g, pts[8], pts[9]); 
		line(g, pts[10], pts[11]); 
		line(g, pts[12], pts[13]); 
		line(g, pts[14], pts[15]); 
	}
	
	private void line(Graphics2D g, Point3D p1, Point3D p2)
	{
		Point2D p1a = world.convert(p1);
		Point2D p2a = world.convert(p2);
		g.drawLine((int)p1a.getX(), (int)p1a.getY(), (int)p2a.getX(), (int)p2a.getY());
	}
	
	private Polygon3D getShape()
	{
		Polygon3D p = new Polygon3D();
		
		p.add(new Point3D(width / 2 + widthOverhang, length / 2 + lengthOverhang));
		p.add(new Point3D(-width / 2 - widthOverhang, length / 2 + lengthOverhang));
		p.add(new Point3D(-width / 2 - widthOverhang, -length / 2 - lengthOverhang));
		p.add(new Point3D(width / 2 + widthOverhang, -length / 2 - lengthOverhang));
		
		return p;
	}
	
	public double getLength()
	{
		return length;
	}
	
	public double getWidth()
	{
		return width;
	}
}
