package com.siebentag.cj.graphics.canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.shot.ShotRecorder;

@Component
public class ShotRecorderPainter extends AbstractCanvasEntity
{
	@Autowired
	private ShotRecorder shotRecorder;
	
    private Color pathColour = new Color(0.5f, 0.5f, 1.0f, 0.4f);
    private Color pointColour = new Color(0.5f, 0.5f, 1.0f, 0.8f);
    private Color joinLineColour = new Color(0.3f, 0.6f, 1.0f, 0.8f);
    
    public void paint(Graphics2D g, double time)
    {
    	if(shotRecorder.getShotPoints() != null && shotRecorder.getShotPoints().size() > 5)
    	{
    		List<Point2D> points = shotRecorder.getShotPoints();
    		int pointCount = points.size();
    		int midpointIdx = pointCount / 2;
    		
    		// draw path
    		Point2D prevPt = null;
			g.setColor(pathColour);
    		for(Point2D nextPt : points)
    		{
    			if(prevPt == null)
    			{
    				prevPt = nextPt;
    			}
    			else
    			{
					g.drawLine((int)prevPt.getX(), (int)prevPt.getY(), (int)nextPt.getX(), (int)nextPt.getY());
    			}
    			
				prevPt = nextPt;
    		}
    		
    		// draw joints
			g.setColor(joinLineColour);
    		Point2D startPt = points.get(0);
    		Point2D midPt = points.get(midpointIdx);
    		Point2D endPt = points.get(pointCount - 1);
    		g.drawLine((int)startPt.getX(), (int)startPt.getY(), (int)midPt.getX(), (int)midPt.getY());
    		g.drawLine((int)endPt.getX(), (int)endPt.getY(), (int)midPt.getX(), (int)midPt.getY());

    		// draw points
			g.setColor(pointColour);
    		for(int i=0; i<points.size(); i++) // don't use iterator, it will through concurrent modification exception
    		{
    			Point2D pt = points.get(i);
    			int x = (int)pt.getX();
    			int y = (int)pt.getY();
				g.fillArc(x, y, 4, 4, 0, 360);
    		}
    	}
    }

	public int getZOrder()
    {
	    return 20;
    }
}
