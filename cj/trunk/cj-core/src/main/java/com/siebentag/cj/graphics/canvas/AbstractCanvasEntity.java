package com.siebentag.cj.graphics.canvas;

import java.awt.Graphics2D;

import com.siebentag.cj.util.math.Time;

public abstract class AbstractCanvasEntity implements CanvasElement
{
	boolean enabled = true;
	
	public abstract void paint(Graphics2D g, Time time);

	public void setEnabled(boolean enabled)
    {
		this.enabled = enabled;
    }

	public boolean isEnabled()
    {
	    return enabled;
    }
	
	public int compareTo(CanvasElement canvasElement) 
	{
		if(getZOrder() > canvasElement.getZOrder())
		{
			return 1;
		}
		else if(getZOrder() < canvasElement.getZOrder())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
