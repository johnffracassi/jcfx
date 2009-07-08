/**
 * 
 */
package com.siebentag.cj.game.event;

import com.siebentag.cj.queue.AbstractEvent;

/**
 * @author jeff
 *
 */
public class ObjectEvent<K> extends AbstractEvent
{
	K obj;
	
	public ObjectEvent(K obj)
	{
		this.obj = obj;
	}
	
    public String getDescription()
    {
	    return "Testing event - " + obj;
    }

    public K getObj()
    {
    	return obj;
    }
}
