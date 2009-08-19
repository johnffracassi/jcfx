/**
 * 
 */
package com.siebentag.cj.game.shot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author jeff
 *
 */
public class ShotVisualiserPanel extends JPanel
{
    private static final long serialVersionUID = 5751970770439774629L;

    ShotModel model;
	
	public ShotVisualiserPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void setShotModel(final ShotModel model)
	{
		this.model = model;
		repaint();
	}
	
	@Override public void paint(final Graphics graphics)
	{
		super.paint(graphics);
		
		if(model != null)
		{
			final Graphics2D g = (Graphics2D)graphics;
			
			final int ox = getWidth() / 2;
			final int oy = getWidth() / 2;
			
			final double ay = 90.0 * Math.sin(model.getAngle().radians());
			final double ax = 90.0 * Math.cos(model.getAngle().radians());
			final double ey = 90.0 * Math.sin(model.getElevation().radians());
			final double ex = 90.0 * Math.cos(model.getElevation().radians());
			
			g.setColor(Color.BLUE);
			g.drawLine((int)ox, (int)oy, (int)(ox+ax), (int)(oy+ay));
			g.setColor(Color.GREEN);
			g.drawLine((int)ox, (int)oy, (int)(ox+ex), (int)(oy-ey));
		}
	}

	@Override public Dimension getPreferredSize()
	{
//		int min = Math.min(getWidth(), getHeight());
//	    return new Dimension(min, min);
		
		return new Dimension(200, 200);
	}
}
