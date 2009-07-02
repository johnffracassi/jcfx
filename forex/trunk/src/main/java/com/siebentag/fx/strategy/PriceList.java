package com.siebentag.fx.strategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PriceList
{
	private Map<String,Double> values;
	private Double[] sorted;
	private boolean sortedDirty;
	private String name;
	private long volume = 0;
	
	public static void main(String[] args)
    {
	    PriceList pl = new PriceList("test");
	    
	    pl.addPrice("1/1/09", 1.00);
	    pl.addPrice("2/1/09", 1.01);
	    pl.addPrice("3/1/09", 1.02);
	    pl.addPrice("4/1/09", Double.NaN);
	    pl.addPrice("5/1/09", 1.1);
	    pl.addPrice("6/1/09", 1.11);
	    pl.addPrice("7/1/09", 1.12);
	    pl.addPrice("8/1/09", 1.15);

	    System.out.println("Sum:    " + pl.sum());
	    System.out.println("Avg:    " + pl.average());
	    System.out.println("Size:   " + pl.size());
	    System.out.println("StDev:  " + pl.stdev());
	    System.out.println("Median: " + pl.median());
	    System.out.println("   40%: " + pl.median(0.4));
	    System.out.println("   60%: " + pl.median(0.6));
	    System.out.println("Range:  " + pl.range());
	    System.out.println("Min/Max:" + pl.min() + " / " + pl.max());
	    System.out.println("Mid66%: " + Arrays.toString(pl.subList(0.67)));
    }
	
	public PriceList()
	{
		this("default");
	}
	
	public PriceList(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	private void sort()
	{
		sorted = new Double[size()];
		values.values().toArray(sorted);
		Arrays.sort(sorted);
	}
	
	private void verifyListExists()
	{
		if(values == null)
		{
			values = new HashMap<String, Double>();
			sortedDirty = false;
		}
	}
	
	public void addPrice(String key, double price)
	{
		verifyListExists();
		
		if(!Double.isNaN(price))
		{
			values.put(key, price);
			sortedDirty = true;
		}
	}
	
	public double sum()
	{
		if(values == null)
		{
			return 0.0;
		}
		
		double total = 0.0;
		
		for(double val : values.values())
		{
			total += val;
		}
		
		return total;
	}
	
	public int size()
	{
		return values == null ? 0 : values.size();
	}
	
	public double average()
	{
		if(values == null)
		{
			return 0.0;
		}
		
		return sum() / (double)size();
	}
	
	public double stdev()
	{
		if(sortedDirty) sort();
		return stdev(sorted);
	}
	
	public double median(double perc)
	{
		if(size() == 0)
		{
			return 0.0;
		}
		
		if(sortedDirty) sort();

		if(sorted.length == 1)
		{
			return sorted[0];
		}
		
		double target = perc * (double)(sorted.length - 1);
		
		// point preceding the target point
		int baseIdx = (int)target;
		double baseVal = sorted[baseIdx];
		
		// percentage of distance between points n and n+1
		double interpolation = target - baseIdx;
		double interpolatedPart = (sorted[baseIdx+1] - sorted[baseIdx]) * interpolation;

		// interpolated value
		double value = baseVal + interpolatedPart;
		
		return value;
	}
	
	public double median()
	{
		return median(0.5);
	}
	
	public double range()
	{
		return max() - min();
	}
	
	public double max()
	{
		if(sortedDirty) sort();
		return sorted[sorted.length - 1];
	}
	
	public double min()
	{
		if(sortedDirty) sort();
		return sorted[0];
	}
	
	public int positive()
	{
		if(sortedDirty) sort();

		int x=0;
		for(int i=0; i<sorted.length; i++)
		{
			if(sorted[i] > 0.0)
			{
				x++;
			}
		}
		return x;
	}
	
	public int negative()
	{
		if(sortedDirty) sort();

		int x=0;
		for(int i=0; i<sorted.length; i++)
		{
			if(sorted[i] < 0.0)
			{
				x++;
			}
		}
		return x;
	}
	
	public double[] subList(double percent)
	{
		int size = size();
		int qty = (int)(percent * (double)size);
		int midPt = size / 2;
		int startPt = midPt - qty / 2;
		
		double[] result = new double[qty];
		for(int i=0; i<qty; i++)
		{
			result[i] = sorted[startPt + i];
		}
		
		return result;
	}
	
	public static double stdev(Double[] data)
	{
		if(data == null || data.length < 2)
		{
			return Double.NaN;
		}

		final int n = data.length;
		double avg = data[0];
		double sum = 0;
		
		for (int i = 1; i < data.length; i++)
		{
			double newavg = avg + (data[i] - avg) / (i + 1);
			sum += (data[i] - avg) * (data[i] - newavg);
			avg = newavg;
		}
		
		return Math.sqrt(sum / (n - 1));
	}
}