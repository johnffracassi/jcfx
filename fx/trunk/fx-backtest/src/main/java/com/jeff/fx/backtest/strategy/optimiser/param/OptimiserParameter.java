package com.jeff.fx.backtest.strategy.optimiser.param;

public abstract class OptimiserParameter<ValueType> {
	
	private String key;
    private String label;
    private String description;
    private double start = 0;
    private double end = 100;
    private double step = 1;
    private double min = 0;
    private double max = 100;

    public abstract Class<?>[] registerFor();
    public abstract ValueType fromString(String val);
    public abstract ValueType fromDouble(double val);
    public abstract String toString(ValueType val);
    public abstract double toDouble(ValueType val);

    public OptimiserParameter() {}

    public ValueType getValue(int step)
    {
        double value = start + (getStepSize() * step);
        return fromDouble(value);
    }

    private final double getStepSize()
    {
        return (end - start) / getStepCount();
    }

    public int getStepCount()
    {
        return (int)((getEnd() - getStart()) / getStep()) + 1;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getStart() {
		return start;
	}

    public void setEnd(String str)
    {
        this.end = toDouble(fromString(str));
    }

    public void setStart(String str)
    {
        this.start = toDouble(fromString(str));
    }

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

    public String getDescription()
    {
        return description == null ? key : description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getMax()
    {
        return max;
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getMin()
    {
        return min;
    }

    public void setMin(double min)
    {
        this.min = min;
    }
}

