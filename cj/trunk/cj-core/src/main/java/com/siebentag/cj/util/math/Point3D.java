package com.siebentag.cj.util.math;

import java.awt.Point;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Point3D implements Cloneable, Serializable
{
	public static final Point3D ORIGIN = new Point3D(0, 0, 0);
	
	private String name = null;
	
	double x, y, z;
	
	public Point3D()
	{
		this(0, 0, 0);
	}
	
	public Point3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3D(double x, double y)
	{
		this(x, y, 0.0);
	}

	public Point3D(String name, double x, double y, double z)
	{
		this(x, y, z); 
		this.name = name;
	}

	public Point3D(Point3D point)
	{
		this(point.name, point.x, point.y, point.z);
	}
	
	public Point3D offset(double dx, double dy, double dz)
	{
		return offset(this, dx, dy, dz);
	}
	
	public void move(double x, double y, double z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public double distanceTo(Point3D to)
	{
		double dx = to.getX() - x;
		double dy = to.getY() - y;
		double dz = to.getZ() - z;
		
		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}
	
	public static Point3D offset(Point3D p3d, double dx, double dy, double dz)
	{
		double x = p3d.getX() + dx;
		double y = p3d.getY() + dy;
		double z = p3d.getZ() + dz;
		
		return new Point3D(x, y, z);
	}
	
	public void translate(double dx, double dy, double dz)
	{
		x += dx;
		y += dy;
		z += dz;
	}
	
	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = (float)x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = (float)y;
	}

	public double getZ()
	{
		return z;
	}

	public void setZ(double z)
	{
		this.z = (float)z;
	}
	
	@Override public boolean equals(Object obj)
	{
		Point3D point = (Point3D)obj;
		return x == point.x && y == point.y && z == point.z;
	}
	
	@Override public int hashCode()
	{
		return toStringNoName().hashCode();
	}
	
	private String toStringNoName()
	{
		return String.format("[%.1f,%.1f,%.1f]", x, y, z);
	}
	
	@Override public String toString()
	{
		return name == null ? toStringNoName() : name;
	}

	/**
     * @return
     */
    public Point flatten()
    {
	    return new Point((int)x, (int)y);
    }

	/**
     * @return
     */
    public Point3D floored()
    {
	    return new Point3D(x, y, 0.0);
    }
}
