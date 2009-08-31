package com.siebentag.cj.util.math;

public class Angle {

	public static final Angle ZERO = Angle.degrees(0.0);
	public static final Angle MAX = Angle.degrees(359.99999999999999999999999999999999999999);
	
	private double radians;

	private Angle(double radians) {
		this.radians = radians;
	}
	
	public static Angle radians(double radians) {
		return new Angle(radians);
	}
	
	public static Angle degrees(double degrees) {
		return new Angle(toRadians(degrees));
	}
	
	public static double toRadians(double degrees) {
		return Math.PI * degrees / 180.0;
	}
	
	public static double toDegrees(double radians) {
		return 180.0 * radians / Math.PI;
	}
	
	public double degrees() {
		return toDegrees(radians);
	}
	
	public double radians() {
		return radians;
	}
	
	@Override
	public String toString() {
		return String.format("%.1f", degrees());
	}
}
