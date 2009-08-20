package com.siebentag.cj.queue;

import com.siebentag.cj.util.math.Time;

public interface QueueItem extends Comparable<QueueItem>
{
	public static final int BEFORE = -1;
	public static final int EQUAL = 0;
	public static final int AFTER = 1;

	Time getTime();
	int getClassPriority();
	Priority getPriority();
	Scope getScope();
	boolean isCancelled();
	void cancel();
	String getDescription();
}
