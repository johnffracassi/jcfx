package com.siebentag.cj.graphics.canvas;

import java.awt.Color;
import java.awt.Graphics2D;

public class BackdropElement extends AbstractCanvasEntity
{
	public void paint(Graphics2D g, double time)
	{
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 800, 600);
	}

	public int getZOrder()
    {
	    return 0;
    }
}
