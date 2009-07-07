package com.siebentag.cj.util.math;


public interface SurfaceModel extends Comparable<SurfaceModel>
{
    public boolean containsPoint(Point3D loc);
    public double getBounceEfficiency();
    public int getZOrder();
    public boolean inPlay();
}
