package com.siebentag.cj.graphics.canvas;

import java.awt.Graphics2D;

public interface CanvasElement extends Comparable<CanvasElement>
{
	public void paint(Graphics2D g, double time);
	public void setEnabled(boolean enabled);
	public boolean isEnabled();
	public int getZOrder();
}
