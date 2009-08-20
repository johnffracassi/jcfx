package com.siebentag.cj.graphics.canvas;

import java.awt.Graphics2D;

import com.siebentag.cj.util.math.Time;

public interface CanvasElement extends Comparable<CanvasElement>
{
	public void paint(Graphics2D g, Time time);
	public void setEnabled(boolean enabled);
	public boolean isEnabled();
	public int getZOrder();
}
