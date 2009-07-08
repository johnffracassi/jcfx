package com.siebentag.cj.util.math;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import static com.siebentag.cj.util.math.Constants.*;

public class TrajectoryPath
{
	private double terminationTime 	 = -1.0;
	private double firstBounceTime 	 = -1.0;
	
	private List<TrajectoryPoint> points;
	
	/**
	 * 
	 * @param resolution
	 */
	public TrajectoryPath(double resolution)
	{
		points = new ArrayList<TrajectoryPoint>();
	}

	/**
	 * 
	 * @param resolution
	 * @param points
	 */
	public TrajectoryPath(double resolution, Point3D[] points)
	{
		this(resolution);
		
		int pointIdx = 0;
		for(Point3D point : points)
		{
			addPoint(new TrajectoryPoint(point, pointIdx * resolution));
		}
	}

	public TrajectoryPath subPath(double startTime)
	{
		TrajectoryPath subPath = new TrajectoryPath(RESOLUTION);
		
		int startIdx = convertTimeToIndex(startTime);
		int endIdx = points.size() - 1;
		for(int i=0; i<endIdx - startIdx; i++)
		{
			TrajectoryPoint oldPoint = points.get(i + startIdx);
			TrajectoryPoint newPoint = new TrajectoryPoint(oldPoint, oldPoint.getTime() - startTime);
			subPath.addPoint(newPoint);
		}
		
		return subPath;
	}
	
	/**
	 * 
	 * @param loc
	 */
	public void addPoint(TrajectoryPoint loc)
	{
		points.add(loc);
		terminationTime = loc.getTime();
		
		// check for a bounce
		if(firstBounceTime < 0.0 && loc.getZ() < 0.05)
		{
			firstBounceTime = loc.getTime();
		}
	}

	public void setTerminateTime(double time)
	{
		terminationTime = time;
	}
	
	public double getTerminationTime()
	{
		return terminationTime;
	}
	
	public void setFirstBounceTime(double firstBounceTime)
	{
		this.firstBounceTime = firstBounceTime;
	}

	public TrajectoryPoint getLocation(double travelTime)
	{
		double exactIdx = travelTime / RESOLUTION;
		int lower = (int)Math.floor(exactIdx);
		int upper = (int)Math.ceil(exactIdx);
		
		double perc = exactIdx - lower;
		Point3D p1 = points.get(getBoundedIndex(points, lower));
		Point3D p2 = points.get(getBoundedIndex(points, upper));
		
		return new TrajectoryPoint(Calculator.interpolate(p1, p2, perc), travelTime);
	}

	@SuppressWarnings("unchecked") 
	private int getBoundedIndex(List list, int idx)
	{
		return min(max(0, idx), list.size() - 1); 
	}
	
	public boolean hasTerminated(double time)
	{
		return time > terminationTime;
	}
	
	public boolean hasBounced(double time)
	{
		return time > firstBounceTime;
	}
	
	public List<TrajectoryPoint> getPoints()
	{
		return points;
	}
	
	protected int convertTimeToIndex(double time)
	{
		if(time > terminationTime)
		{
			return convertTimeToIndex(terminationTime);
		}
		else
		{
			int index = (int)(time / RESOLUTION); 
			return max(0, min(index, points.size() - 1));
		}
	}

	public double getFirstBounceTime()
	{
		return firstBounceTime;
	}
	
	/**
     * @return
     */
    public TrajectoryPoint getFirstBounceLocation()
    {
	    return getPoints().get(convertTimeToIndex(firstBounceTime));
    }
    
    public TrajectoryPoint getLocationAtY(double y)
    {
    	return getLocation(getTimeAtY(y));
    }
    
    /**
     * 
     * @param y
     * @return
     */
    public double getTimeAtY(double y)
    {
    	int pointCount = points.size();
    	
    	for(int i=0; i<pointCount; i++)
    	{
    		if(points.get(i).getY() <= y)
    		{
    			return (double)i * RESOLUTION;
    		}
    	}
    	
    	return 0.0;
    }
}