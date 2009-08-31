package com.siebentag.cj.time;

import java.util.EnumMap;

import org.springframework.stereotype.Component;

import com.siebentag.cj.util.math.Time;
import com.siebentag.cj.util.math.TimeScope;

@Component
public class RealTimeKeeper implements TimeKeeper {
	
	private EnumMap<TimeScope, Long> scopeTime;
	public static final double MULTIPLIER = 1000000000.0;
	
	public RealTimeKeeper() {
		
		long time = System.nanoTime();
		
		scopeTime = new EnumMap<TimeScope, Long>(TimeScope.class);
		
		for(TimeScope scope : TimeScope.values()) {
			scopeTime.put(scope, time);
		}
	}

	public Time getTime(TimeScope scope) {

		long time = System.nanoTime();
		long scopeStartTime = scopeTime.get(scope);
		
		double seconds = (time - scopeStartTime) / MULTIPLIER;
		
		return new Time(time, seconds, scope);		
	}
}
