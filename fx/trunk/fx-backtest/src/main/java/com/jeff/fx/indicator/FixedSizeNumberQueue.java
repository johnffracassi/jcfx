package com.jeff.fx.indicator;

import java.util.concurrent.ArrayBlockingQueue;

public class FixedSizeNumberQueue extends ArrayBlockingQueue<Double> {

	private static final long serialVersionUID = 6506626739170263513L;

	private int capacity = 0;
	private double sum = 0.0;
	private double high = Double.MIN_VALUE;
	private double low = Double.MAX_VALUE;
	private double head = Double.NaN;
	private double tail = Double.NaN;

	public FixedSizeNumberQueue(int capacity) {
		super(capacity);
		this.capacity = capacity;
	}

	public double value() {
		return average();
	}

	public boolean isFull() {
		return (size() == capacity);
	}

	@Override
	public boolean add(Double val) {
		sum += val;

		if (remainingCapacity() == 0) {
			remove();
		}

		head = (size() == 0) ? val : peek();
		tail = val;

		if (val > high) {
			high = val;
		}
		if (val < low) {
			low = val;
		}

		return super.add(val);
	}

	public Double poll() {

		double num = super.poll();

		sum -= num;

		// has the highest value been removed?
		if (num == high) {
			high = Double.MIN_VALUE;
			for (Double x : this) {
				if (x > high)
					high = x;
			}
		}

		// has the lowest value been removed?
		if (num == low) {
			low = Double.MAX_VALUE;
			for (Double x : this) {
				if (x < low)
					low = x;
			}
		}

		return num;
	}

	public int capacity() {
		return capacity;
	}

	public double sum() {
		return sum;
	}

	public double average() {

		if (size() == 0)
			return 0.0;

		return sum / size();
	}

	public double open() {
		return head;
	}

	public double close() {
		return tail;
	}

	public double high() {
		return high;
	}

	public double low() {
		return low;
	}

	public double direction() {
		return head - tail;
	}
}
