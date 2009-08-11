package com.siebentag.cj.graphics.canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.springframework.beans.factory.annotation.Autowired;

import com.siebentag.cj.graphics.World;
import com.siebentag.cj.mvc.BallController;
import com.siebentag.cj.util.math.TrajectoryPath;
import com.siebentag.cj.util.math.TrajectoryPoint;

//@Component
public class TrajectoryPathPainter extends AbstractCanvasEntity
{
	@Autowired
	private BallController ballController;
	
	@Autowired
	private World world;
	
	private Color pathColour = new Color(1.0f, 0.5f, 0.5f, 0.2f);
	private Color pointColour = new Color(1.0f, 0.5f, 0.5f, 0.7f);
	
	public void paint(Graphics2D g, double time)
	{
		TrajectoryPath trajectoryPath = ballController.getTrajectoryPath();
		
		if(trajectoryPath != null && trajectoryPath.getPoints().size() > 1)
		{
			Point2D prevPt = null;
			
			for(TrajectoryPoint pt : trajectoryPath.getPoints())
			{
				Point2D nextPt = world.convert(pt);
				
				if(prevPt == null)
				{
					prevPt = nextPt;
				}
				else
				{
					g.setColor(pathColour);
					g.drawLine((int)prevPt.getX(), (int)prevPt.getY(), (int)nextPt.getX(), (int)nextPt.getY());
					g.setColor(pointColour);
					g.drawRect((int)prevPt.getX(), (int)prevPt.getY(), 1, 1);
				}
				
				prevPt = nextPt;
			}
		}
	}

	public int getZOrder()
    {
	    return 19;
    }
}
