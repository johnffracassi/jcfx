package com.siebentag.cj.time;

import com.siebentag.cj.util.math.Time;

public interface TimeKeeper {
	public Time getAppTime();
	public Time getBallTime();
}
