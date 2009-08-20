package com.siebentag.cj;

import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.cj.game.action.Action;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.queue.ManagedQueue;
import com.siebentag.cj.queue.QueueItem;
import com.siebentag.cj.time.TimeKeeper;
import com.siebentag.cj.util.math.Time;
import com.siebentag.cj.util.math.TimeScope;

@Component
public class Manager extends Thread
{
	private static final Logger log = Logger.getLogger(Manager.class);

	@Autowired
	private TimeKeeper timeKeeper;

	@Autowired
	private ManagedQueue managedQueue;
	
	@Autowired
	private List<EventListener> eventListeners;
	
	/**
	 * Don't ever want this thread to die while the game is running, 
	 * so make sure all exceptions are caught and logged.
	 */
	public void run()
	{
		setName("ManagerThread");
		
		log.info("Starting action/event queue polling");	
		
		for(EventListener event : eventListeners)
		{
			log.info("registered " + event.getClass() + " as event listener");
		}

		while(true)
		{
			// this instant of time should be used by everything 
			Time time = timeKeeper.getTime(TimeScope.Application);
			
			log.trace(String.format("polling queue: time=%.1f size=%d", time, managedQueue.size()));
			
			try
			{
				List<QueueItem> items = managedQueue.poll(time);
				
				for(QueueItem item : items)
				{
					if(item instanceof Event)
					{
						notifyEventListeners((Event)item);
					}
					else if(item instanceof Action)
					{
						executeAction((Action)item);
					}
				}
				
				sleep(20);
			}
			catch(Exception ex)
			{
				log.error("Error in manager thread", ex);
			}
		}
	}
	
	/**
	 * Execute an action, catch exceptions so it doesn't stop other actions
	 * from executing
	 * 
	 * @param action
	 */
	private void executeAction(Action action)
	{
		try
		{
			log.debug("Executing action " + action);
			action.run();
		}
		catch(Exception ex)
		{
			Log.error("Error executing action '" + action + "'", ex);
		}
	}
	
	/**
	 * Notify all listeners of a particular event, catch any exceptions
	 * thrown by listeners so events can still be propagated through to
	 * the rest of the listeners
	 * 
	 * @param event
	 */
	private void notifyEventListeners(Event event)
	{
		log.debug("Notifying listeners of event " + event);

		for(EventListener listener : eventListeners)
		{
			try
			{
				listener.event(event);
			}
			catch(Exception ex)
			{
				log.error("Error while notifying '" + listener + "' of '" + event + "'", ex);
			}
		}
	}
}
