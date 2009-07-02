package com.siebentag.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Notify all indicators and strategies of all ticks in the list
 * 
 * @param ticks
 */
public class MultiThreadedExecutor
{
	private List<Runnable> tasks;
	
	public MultiThreadedExecutor(List<Runnable> tasks)
	{
		this.tasks = Collections.unmodifiableList(tasks);
	}
	
	public void run()
	{
		int threads = Runtime.getRuntime().availableProcessors();
		
		System.out.println("Spreading " + tasks.size() + " over " + threads + " threads");
		
		// create the executor
		ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 1, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
		
		// execute the threads
		for(Runnable task : tasks)
		{
			executor.execute(task);
		}
		
		// shutdown all threads
		executor.shutdown();

		// wait for threads to complete before returning
		try
		{
			executor.awaitTermination(10, TimeUnit.MINUTES);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}	
