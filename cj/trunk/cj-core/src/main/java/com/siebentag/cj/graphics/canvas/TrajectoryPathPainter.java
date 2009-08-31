package com.siebentag.cj.graphics.canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.graphics.World;
import com.siebentag.cj.mvc.BallController;
import com.siebentag.cj.util.math.Time;
import com.siebentag.cj.util.math.TrajectoryPath;
import com.siebentag.cj.util.math.TrajectoryPoint;

@Component
public class TrajectoryPathPainter extends AbstractCanvasEntity
{
	@Autowired
	private BallController ballController;
	
	@Autowired
	private World world;
	
	private Color pathColour = new Color(1.0f, 0.5f, 0.5f, 0.2f);
	private Color pointColour = new Color(1.0f, 0.5f, 0.5f, 0.7f);
	
	public void paint(Graphics2D g, Time time)
	{
		TrajectoryPath trajectoryPath = ballController.getTrajectoryPath();
		
		if(trajectoryPath != null && trajectoryPath.getPoints().size() > 1)
		{
			Point2D prevPt = null;
			
			for(TrajectoryPoint pt : trajectoryPath.getPoints())
			{
				Point2D nextPt = world.convert(pt);
				Point2D shadowPt = world.convert(pt.floored());
				
				if(prevPt == null)
				{
					prevPt = nextPt;
				}
				else
				{
					g.setColor(Color.BLACK);
					g.drawRect((int)shadowPt.getX(), (int)shadowPt.getY(), 1, 1);

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
