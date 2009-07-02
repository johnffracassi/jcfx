package com.siebentag.fx.backtest;

import java.util.LinkedList;
import java.util.List;

import com.siebentag.fx.TickDataPoint;

/**
 * Notify all indicators and strategies of all ticks in the list
 * 
 * @param ticks
 */
public class SingleThreadedStrategyExecutor implements StrategyExecutor
{
	private List<Strategy> strategies;
	private List<TickDataPoint> ticks;
	
	public SingleThreadedStrategyExecutor(List<Strategy> strategies, List<TickDataPoint> ticks)
	{
		this.ticks = ticks;
		this.strategies = new LinkedList<Strategy>(strategies);
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
		
		for(Runnable task : tasks)
		{
			task.run();
		}
	}
}
