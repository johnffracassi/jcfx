package com.siebentag.cj.graphics.field;

import java.awt.Color;
import java.awt.Graphics2D;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.Polygon3D;
import com.siebentag.cj.graphics.World;
import com.siebentag.cj.graphics.canvas.AbstractCanvasEntity;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.cj.util.math.Time;

@Component
public class Stumps extends AbstractCanvasEntity 
{
	@Autowired 
	private World world;
	
	@Autowired
	private Pitch pitch;
	
	private static double height = .7;
	private static double gap = .2;
	private static Color colour = new Color(230, 230, 128);
	
	public int getZOrder()
	{
		return 10;
	}
	
	public void paint(Graphics2D g, Time time)
	{
		g.setColor(colour);
		g.drawPolygon(world.convert(getStump(-gap, pitch.getLength() / 2)));
		g.drawPolygon(world.convert(getStump(0, pitch.getLength() / 2)));
		g.drawPolygon(world.convert(getStump(gap, pitch.getLength() / 2)));
		g.drawPolygon(world.convert(getStump(-gap, -pitch.getLength() / 2)));
		g.drawPolygon(world.convert(getStump(0, -pitch.getLength() / 2)));
		g.drawPolygon(world.convert(getStump(gap, -pitch.getLength() / 2)));
	}
	
	private Polygon3D getStump(double x, double y)
	{
		Polygon3D stump = new Polygon3D();
		
		stump.add(new Point3D(x, y, 0.0));
		stump.add(new Point3D(x, y, height));
		
		return stump;
	}
}
