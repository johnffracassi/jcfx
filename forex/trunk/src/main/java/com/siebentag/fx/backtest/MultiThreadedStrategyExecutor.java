package com.siebentag.fx.backtest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.siebentag.fx.TickDataPoint;

/**
 * Notify all indicators and strategies of all ticks in the list
 * 
 * @param ticks
 */
public class MultiThreadedStrategyExecutor implements StrategyExecutor
{
	private List<Strategy> strategies;
	private List<TickDataPoint> ticks;
	
	public MultiThreadedStrategyExecutor(List<Strategy> strategies, List<TickDataPoint> ticks)
	{
		this.ticks = Collections.unmodifiableList(ticks);
		this.strategies = Collections.unmodifiableList(strategies);
	}
	
	public void run()
	{
		// create the task queue
		List<Runnable> tasks = new LinkedList<Runnable>();
		for(Strategy strategy : strategies)
		{
			Runnable task = new StrategyTask(strategy, ticks);
			tasks.add(task);
		}
		
		// create the executor
		ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
		
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
