package com.siebentag.cj.graphics.canvas;

import java.awt.Color;
import java.awt.Graphics2D;

import com.siebentag.cj.util.math.Time;

public class BackdropElement extends AbstractCanvasEntity
{
	public void paint(Graphics2D g, Time time)
	{
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 800, 600);
	}

	public int getZOrder()
    {
	    return 0;
    }
}
