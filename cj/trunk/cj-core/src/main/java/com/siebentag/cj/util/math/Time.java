package com.siebentag.cj.util.math;

public class Time implements Comparable<Time> {
	
	private double time;
	private Scope scope;
	
	public Time(double time, Scope scope) {
		this.time = time;
		this.scope = scope;
	}
	
	public double getTime() {
		return time;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public enum Scope {
		Ball, Application
	}

	public int compareTo(Time obj) {
		
		if(scope != obj.scope)
			throw new IllegalArgumentException("Cannot compare Times with different scopes");
		
		if(time > obj.time) return 1;
		else if(time < obj.time) return -1;
		else return 0;
	}
}
