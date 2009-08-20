package com.siebentag.cj.time;

import org.springframework.stereotype.Component;

import com.siebentag.cj.util.math.Time;
import com.siebentag.cj.util.math.TimeScope;

@Component
public class RealTimeKeeper implements TimeKeeper {
	
	private Long ballStartTime = null;
	private Long appStartTime = null;
	private Long pathStartTime = null;

	public RealTimeKeeper() {
	}

	public Time getTime(TimeScope scope) {

		long time = System.nanoTime();
		
		switch (scope) {
		case Application:
			if (appStartTime == null)
				appStartTime = System.nanoTime();
			return new Time(time, (time - appStartTime) / Time.MULTIPLIER, scope);
			
		case Ball:
			if (ballStartTime == null)
				ballStartTime = System.nanoTime();
			return new Time(time, (time - ballStartTime) / Time.MULTIPLIER, scope);
			
		case Path:
			if (pathStartTime == null)
				pathStartTime = System.nanoTime();
			return new Time(time, (time - pathStartTime) / Time.MULTIPLIER, scope);
			
		default:
			throw new IllegalArgumentException("Unsupported TimeScope: " + scope);
		}
	}
}
