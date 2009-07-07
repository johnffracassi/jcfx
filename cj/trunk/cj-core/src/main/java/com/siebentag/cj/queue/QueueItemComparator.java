package com.siebentag.cj.queue;

import java.util.Comparator;

public class QueueItemComparator implements Comparator<QueueItem> 
{
	public int compare(QueueItem o1, QueueItem o2) 
	{
		return o1.compareTo(o2);
	}
}
