package com.siebentag.cj.util.math;

import static java.lang.Math.*;

import java.awt.geom.Point2D;


public class Calculator
{
	public static final double distance(Point3D p1, Point3D p2)
	{
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		double dz = p1.getZ() - p2.getZ();
		
		double distance = dx*dx + dy*dy + dz*dz;
		
		return distance > 0.0 ? Math.sqrt(distance) : 0.0;
	}
	
	public static final double distance(Point2D p1, Point2D p2)
	{
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();

		return distance(dx, dy);
	}
	
	public static final double distance(double dx, double dy)
	{
		double distance = dx*dx + dy*dy;
		
		return distance > 0.0 ? Math.sqrt(distance) : 0.0;
	}
	
	public static final Point3D interpolate(Point3D start, Point3D end, double percentage)
	{
		double dx = end.x - start.x;
		double dy = end.y - start.y;
		double dz = end.z - start.z;
		
		return new Point3D(start.x + dx * percentage, start.y + dy * percentage, start.z + dz * percentage);
	}
	
	public static final Angle angle(double dx, double dy)
	{
		if(dx >= 0.0 && dy < 0.0) // quadrant 1
		{
			return Angle.radians(atan(dx / -dy));
		}
		else if(dx >= 0.0 && dy >= 0.0) // quadrant 2
		{
			return Angle.radians(atan(dy / dx) + (PI / 2));
		}
		else if(dx < 0.0 && dy >= 0.0) // quadrant 3
		{
			return Angle.radians(atan(-dx / dy) + PI);
		}
		else // quadrant 4
		{
			return Angle.radians(atan(-dy / -dx) + 3*PI / 2);
		}
	}

	public static final double mod(double number, double divisor)
	{
		return number - (floor(number, divisor));
	}
	
	public static final double floor(double number, double divisor)
	{
		return ((int)(number / divisor)) * divisor;
	}
	
	public static final double getComponentValue(double speed, Angle direction, Angle elevation, VectorComponent component)
	{
		if(component == VectorComponent.Resultant)
		{
			return speed;
		}
		else
		{
			if(component == VectorComponent.X)
			{
				return speed * Math.sin(direction.radians());
			}
			else if(component == VectorComponent.Y)
			{
				return speed * Math.cos(direction.radians());
			}
			else if(component == VectorComponent.Z)
			{
				return speed * Math.sin(elevation.radians());
			}
		}
		
		throw new IllegalArgumentException("Unknown vector component (" + VectorComponent.class.getSimpleName() + "." + component + ")");
	}
	
	public static final double getComponentValue(double speed, Angle direction, VectorComponent component)
	{
		if(component == VectorComponent.Z)
		{
			throw new IllegalArgumentException("Must specify elevation to find Z component");
		}
		else
		{
			return getComponentValue(speed, direction, Angle.ZERO, component);
		}
	}
}
