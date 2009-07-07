package com.siebentag.cj.graphics.overlay;

import java.awt.Color;
import java.awt.Graphics2D;

import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.canvas.AbstractCanvasEntity;

@Component
public class FPSElement extends AbstractCanvasEntity
{
	private double startTime = -1.0;
	private long frameCount;

	private Color background = new Color(1.0f, 1.0f, 1.0f, 0.75f);
	
	@Override public void paint(Graphics2D g, double time)
	{
		if(startTime < 0.0)
		{
			startTime = time;
		}
		
		frameCount ++;
		
		g.setColor(background);
		g.fillRect(10, 10, 150, 20);
		
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, 150, 20);
		
		String msg = String.format("%d / %.2fs / %.1ffps", frameCount, time, (frameCount / time));
		g.drawString(msg, 20, 25);
	}

	public int getZOrder()
    {
	    return 1000;
    }
}
