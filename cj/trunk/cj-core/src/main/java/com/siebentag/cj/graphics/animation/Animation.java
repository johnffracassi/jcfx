package com.siebentag.cj.graphics.animation;


public abstract class Animation
{
	private String name;
	private int width;
	private int height;
	
	public abstract AnimationFrame getFrameByTime(double time);
	
	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}

