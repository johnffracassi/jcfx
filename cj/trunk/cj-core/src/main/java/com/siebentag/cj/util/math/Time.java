package com.siebentag.cj.util.math;

public class Time implements Comparable<Time> {
	
	public static final Time ZERO = new Time(0, 0.0, TimeScope.Absolute);
	public static final double MULTIPLIER = 1000000000.0;
	
	private TimeScope scope;
	private double time;
	private long nanoTime;
	
	public Time(long nanos, double time, TimeScope scope) {
		this.nanoTime = nanos;
		this.time = time;
		this.scope = scope;
	}
	
	public double getTime() {
		return time;
	}
	
	public boolean isBefore(Time time) {
		return (nanoTime < time.getAbsoluteTime()); 
	}
	
	public boolean isAfter(Time time) {
		return (nanoTime > time.getAbsoluteTime());
	}
	
	public long getAbsoluteTime() {
		return nanoTime;
	}
	
	public Time add(Time time) {
		
		if(time == null) 
			return this;
		
		return new Time(nanoTime + (long)(time.getTime() * MULTIPLIER), this.time + time.getTime(), scope);
	}
	
	public Time subtract(Time time) {
		
		if(time == null)
			return this;
		
		return new Time(nanoTime - (long)(time.getTime() * MULTIPLIER), this.time - time.getTime(), scope);
	}
	
	public Time add(double seconds) {
		return new Time((long)(nanoTime + seconds * MULTIPLIER), time + seconds, scope);
	}
	
	public Time subtract(double seconds) {
		return new Time((long)(nanoTime - seconds * MULTIPLIER), time - seconds, scope);
	}
	
	public TimeScope getScope() {
		return scope;
	}
	
	public int compareTo(Time obj) {
		if(isAfter(obj)) return 1;
		else if(isBefore(obj)) return -1;
		else return 0;
	}
	
	@Override
	public String toString() {
		return String.format("[%.1fs/%s]", time, scope);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof Time && ((Time)obj).time == time);
	}
	
	@Override
	public int hashCode() {
		return new Long(nanoTime).hashCode();
	}
}
