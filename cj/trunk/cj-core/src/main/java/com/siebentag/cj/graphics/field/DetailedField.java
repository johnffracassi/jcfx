package com.siebentag.cj.graphics.field;

import static java.lang.Math.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.siebentag.cj.Config;
import com.siebentag.cj.graphics.Polygon3D;
import com.siebentag.cj.graphics.World;
import com.siebentag.cj.graphics.WorldChangeListener;
import com.siebentag.cj.graphics.canvas.AbstractCanvasEntity;
import com.siebentag.cj.util.math.Point3D;

@Component
public class DetailedField extends AbstractCanvasEntity implements WorldChangeListener
{
	@Autowired
	World world;
	
	private double length = 95;
	private double width = 65;
	
	private BufferedImage unscaledImage1 = null;
	private BufferedImage unscaledImage2 = null;
	private BufferedImage scaledImage1 = null;
	private BufferedImage scaledImage2 = null;
	
	private Point2D origin = new Point();
	
	private Polygon fieldPolygon = null;
	
	public DetailedField()
	{
	}
	
	public int getZOrder()
	{
		return 0;
	}
	
	/**
	 * Event handler method called when the size of the viewport is changed
	 */
	public void worldChanged()
	{
		if(unscaledImage1 == null || unscaledImage2 == null)
		{
			loadImages();
		}
		
		rescaleImages();
	}
	
	/**
	 * Load bitmaps from disk
	 */
	private void loadImages()
	{
		try
		{
			unscaledImage1 = ImageIO.read(new ClassPathResource("/images/ground1.jpg").getFile());
			unscaledImage2 = ImageIO.read(new ClassPathResource("/images/ground1.jpg").getFile());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void rescaleImages()
	{
		Point2D p1 = world.convert(-width, length);
		Point2D p2 = world.convert(width, -length);
		origin = p1;
		
		double desiredWidth = p2.getX() - p1.getX();
		double desiredHeight = p2.getY() - p1.getY();
		
		int width = Math.max(1, (int)desiredWidth);
		int height = Math.max(1, (int)desiredHeight);
		
		scaledImage1 = rescaleImage(unscaledImage1, width, height);
		scaledImage2 = rescaleImage(unscaledImage2, width, height);
		
		fieldPolygon = world.convert(buildBoundaryShape());
	}

	private BufferedImage rescaleImage(BufferedImage img, int width, int height)
	{
		BufferedImage scaled = new BufferedImage(width, height, img.getType());
		scaled.getGraphics().drawImage(unscaledImage2, 0, 0, width, height, null);
		return scaled;
	}
	
	@Override
    public void paint(Graphics2D g, double time)
	{
		g.drawImage(scaledImage1, (int)origin.getX(), (int)origin.getY(), null);

		g.setColor(Color.RED);
		g.drawRect((int)origin.getX(), (int)origin.getY(), scaledImage1.getWidth(), scaledImage1.getHeight());
		g.fillRect((int)origin.getX(), (int)origin.getY(), 3, 3);
		
		g.setColor(Color.WHITE);
		g.drawPolygon(fieldPolygon);
	}
	
	private Polygon3D buildBoundaryShape()
	{
		Polygon3D p = new Polygon3D();

		for(double t = 0.0; t <= 360.0; t += 2.0)
		{
			double x = width * cos(toRadians(t));
			double y = length * sin(toRadians(t));
			p.add(new Point3D(x, y, 0.0));
		}
		
		return p;
	}
}
