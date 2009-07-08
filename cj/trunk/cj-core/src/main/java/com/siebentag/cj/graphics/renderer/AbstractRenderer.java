package com.siebentag.cj.graphics.renderer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

import org.springframework.beans.factory.annotation.Autowired;

import com.siebentag.cj.graphics.World;
import com.siebentag.cj.graphics.sprite.SpriteManager;
import com.siebentag.cj.util.math.Point3D;

public abstract class AbstractRenderer<StateClass> 
{
	@Autowired
	private World world;
	
	@Autowired
	private SpriteManager spriteManager;
	
	public abstract void render(Graphics2D g, Point3D loc, StateClass state, double animationTime);

	public AbstractRenderer()
	{
	}

	protected void drawImage(Graphics2D g, Point3D loc, Image img)
	{
		Point2D loc2d = world.convert(loc);
		g.drawImage(img, (int) loc2d.getX() - (img.getWidth(null) / 2), (int) loc2d.getY() - img.getHeight(null), null);
	}

	public SpriteManager getSpriteManager() 
	{
		return spriteManager;
	}

	public void setSpriteManager(SpriteManager spriteManager) 
	{
		this.spriteManager = spriteManager;
	}

	public World getWorld()
	{
		return world;
	}

	public void setWorld(World world)
	{
		this.world = world;
	}
}
