package com.siebentag.cj.util.math;

import static com.siebentag.cj.util.math.Constants.RESOLUTION;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryPath {
	private Time terminationTime = null;
	private Time firstBounceTime = null;

	private List<TrajectoryPoint> points;

	/**
	 * 
	 * @param resolution
	 */
	public TrajectoryPath(double resolution) {
		points = new ArrayList<TrajectoryPoint>();
	}

	/**
	 * 
	 * @param resolution
	 * @param points
	 */
	public TrajectoryPath(double resolution, Point3D[] points) {

		this(resolution);

		int pointIdx = 0;
		for (Point3D point : points) {
			addPoint(new TrajectoryPoint(point, new Time(0, pointIdx
					* resolution, TimeScope.Path)));
		}
	}

	public TrajectoryPath subPath(Time startTime) {
		TrajectoryPath subPath = new TrajectoryPath(RESOLUTION);

		int startIdx = convertTimeToIndex(startTime);
		int endIdx = points.size() - 1;
		for (int i = 0; i < endIdx - startIdx; i++) {
			TrajectoryPoint oldPoint = points.get(i + startIdx);
			TrajectoryPoint newPoint = new TrajectoryPoint(oldPoint, new Time(
					oldPoint.getTime().getAbsoluteTime(), oldPoint.getTime()
							.getTime()
							- startTime.getTime(), TimeScope.Path));
			subPath.addPoint(newPoint);
		}

		return subPath;
	}

	/**
	 * 
	 * @param loc
	 */
	public void addPoint(TrajectoryPoint loc) {
		points.add(loc);
		terminationTime = loc.getTime();

		// check for a bounce
		if (firstBounceTime == null && loc.getZ() < 0.05) {
			firstBounceTime = loc.getTime();
		}
	}

	public void setTerminateTime(Time time) {
		terminationTime = time;
	}

	public Time getTerminationTime() {
		return terminationTime;
	}

	public void setFirstBounceTime(Time firstBounceTime) {
		this.firstBounceTime = firstBounceTime;
	}

	public TrajectoryPoint getLocation(Time time) {
		
		if (hasTerminated(time)) {
			time = terminationTime;
		}

		double exactIdx = time.getTime() / RESOLUTION;
		int lower = (int) Math.floor(exactIdx);
		int upper = (int) Math.ceil(exactIdx);

		double perc = exactIdx - lower;
		Point3D p1 = points.get(getBoundedIndex(points, lower));
		Point3D p2 = points.get(getBoundedIndex(points, upper));

		return new TrajectoryPoint(Calculator.interpolate(p1, p2, perc), time);
	}

	@SuppressWarnings("unchecked")
	private int getBoundedIndex(List list, int idx) {
		return min(max(0, idx), list.size() - 1);
	}

	public boolean hasTerminated(Time time) {
		return time.isAfter(terminationTime);
	}

	public boolean hasBounced(Time time) {
		return time.isAfter(firstBounceTime);
	}

	public List<TrajectoryPoint> getPoints() {
		return points;
	}

	protected int convertTimeToIndex(Time time) {
		if (time.isAfter(terminationTime)) {
			return convertTimeToIndex(terminationTime);
		} else {
			int index = (int) (time.getTime() / RESOLUTION);
			return max(0, min(index, points.size() - 1));
		}
	}

	public Time getFirstBounceTime() {
		return firstBounceTime;
	}

	/**
	 * @return
	 */
	public TrajectoryPoint getFirstBounceLocation() {
		return getPoints().get(convertTimeToIndex(firstBounceTime));
	}

	public TrajectoryPoint getLocationAtY(double y) {
		return getLocation(getTimeAtY(y));
	}

	public double getVelocityAtTime(Time time) {
		
		int idx = convertTimeToIndex(time);

		if (idx > 0) {
			double displacement = Calculator.distance(points.get(idx), points
					.get(idx - 1));
			return displacement / RESOLUTION;
		}

		return 0.0;
	}

	/**
	 * 
	 * @param y
	 * @return
	 */
	public Time getTimeAtY(double y) {
		
		int pointCount = points.size();

		for (int i = 0; i < pointCount; i++) {
			TrajectoryPoint pt = points.get(i);
			if (pt.getY() <= y) {
				return pt.getTime();
			}
		}

		return null;
	}
}