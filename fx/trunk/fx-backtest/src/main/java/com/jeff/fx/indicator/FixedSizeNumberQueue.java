package com.jeff.fx.indicator;

import java.util.concurrent.ArrayBlockingQueue;

public class FixedSizeNumberQueue extends ArrayBlockingQueue<Float> {

	private static final long serialVersionUID = 6506626739170263513L;

	private int capacity = 0;
	private float sum = 0.0f;
	private float high = Float.MIN_VALUE;
	private float low = Float.MAX_VALUE;
	private float head = Float.NaN;
	private float tail = Float.NaN;

	public FixedSizeNumberQueue(int capacity) {
		super(capacity);
		this.capacity = capacity;
	}

	public float value() {
		return average();
	}

	public boolean isFull() {
		return (size() == capacity);
	}

	@Override
	public boolean add(Float val) {
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

	public Float poll() {

		float num = super.poll();

		sum -= num;

		// has the highest value been removed?
		if (num == high) {
			high = Float.MIN_VALUE;
			for (Float x : this) {
				if (x > high)
					high = x;
			}
		}

		// has the lowest value been removed?
		if (num == low) {
			low = Float.MAX_VALUE;
			for (Float x : this) {
				if (x < low)
					low = x;
			}
		}

		return num;
	}

	public int capacity() {
		return capacity;
	}

	public float sum() {
		return sum;
	}

	public float average() {

		if (size() == 0)
			return 0.0f;

		return sum / size();
	}

	public float open() {
		return head;
	}

	public float close() {
		return tail;
	}

	public float high() {
		return high;
	}

	public float low() {
		return low;
	}

	public float direction() {
		return head - tail;
	}
}
