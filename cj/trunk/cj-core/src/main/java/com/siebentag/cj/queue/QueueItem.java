package com.siebentag.cj.queue;

public interface QueueItem extends Comparable<QueueItem>
{
	public static final int BEFORE = -1;
	public static final int EQUAL = 0;
	public static final int AFTER = 1;

	double getTime();
	int getClassPriority();
	Priority getPriority();
	Scope getScope();
	boolean isCancelled();
	void cancel();
	String getDescription();
}
