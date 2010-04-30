package com.jeff.fx.indicator.advice;

import com.jeff.fx.common.CandleDataPoint;

public class HighLowFinder {

	private static final int HIGH = 1;
	private static final int LOW = -1;
	
	private int lookingFor = 0;
	
	private int countSinceLastHigh = 0;
	private int countSinceLastLow = 0;
	
	private double currentHigh = Double.MIN_VALUE;
	private double currentLow = Double.MAX_VALUE;
	
	public void add(CandleDataPoint data) {
		
		if(data.getSellHigh() > currentHigh) {
			currentHigh = data.getSellHigh();
			countSinceLastHigh = 0;
			lookingFor = LOW;
			System.out.println("New high = " + data);
		} else if(data.getSellLow() < currentLow) {
			currentLow = data.getSellLow();
			countSinceLastLow = 0;
			lookingFor = HIGH;
			System.out.println("New low = " + data);
		}

		countSinceLastLow ++;
		countSinceLastHigh ++;
	}
}
