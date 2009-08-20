package com.siebentag.cj.time;

import com.siebentag.cj.util.math.Time;
import com.siebentag.cj.util.math.TimeScope;

public interface TimeKeeper {
	public Time getTime(TimeScope scope);
}
