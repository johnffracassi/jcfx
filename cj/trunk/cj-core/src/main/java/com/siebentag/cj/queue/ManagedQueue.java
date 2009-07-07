package com.siebentag.cj.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ManagedQueue
{
	private static final Logger log = Logger.getLogger(ManagedQueue.class);

	private Queue<QueueItem> queue;
	
	public ManagedQueue()
	{
		queue = new PriorityQueue<QueueItem>();
	}

	/**
	 * Remove all items from the head of the queue that occur before time
	 * @param time
	 * @return
	 */
	public List<QueueItem> poll(double time)
	{
		List<QueueItem> items = new ArrayList<QueueItem>();
		
		if(size() > 0)
		{
			while(peek() != null && peek().getTime() <= time)
			{
				items.add(poll());
			}
		}
		
		return items;
	}
	
	/**
	 * Cancel all items in a particular scope
	 * 
	 * @param scope
	 */
	public void cancel(Scope scope)
	{
		for(QueueItem item : queue)
		{
			if(!item.isCancelled())
			{
				item.cancel();
			}
		}
	}
	
	public void add(QueueItem item)
	{
		log.debug("Add item to queue: " + item);
		queue.add(item);
	}
	
	private QueueItem poll()
	{
		return queue.poll();
	}
	
	private QueueItem peek()
	{
		return queue.peek();
	}
	
	public int size()
	{
		return queue.size();
	}
	
	public boolean isEmpty()
	{
		return size() == 0;
	}
}
