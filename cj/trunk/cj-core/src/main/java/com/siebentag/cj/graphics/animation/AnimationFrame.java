package com.siebentag.cj.graphics.animation;

import java.awt.Image;

public class AnimationFrame
{
	private Image image;
	private double displayTime;
	private String description;
	
	public AnimationFrame(Image image, double displayTime, String description)
	{
		this.image = image;
		this.displayTime = displayTime;
		this.description = description;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public int getWidth()
	{
		return image.getWidth(null);
	}
	
	public int getHeight()
	{
		return image.getHeight(null);
	}
	
	public double getDisplayTime()
	{
		return displayTime;
	}

	public String getDescription()
    {
    	return description;
    }

	public void setDescription(String description)
    {
    	this.description = description;
    }
}