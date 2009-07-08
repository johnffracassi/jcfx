/**
 * 
 */
package com.siebentag.cj.game.shot;

import com.siebentag.cj.game.event.ShotRecorderEvent;
import com.siebentag.cj.queue.EventListener;


/**
 * @author jeff
 *
 */
public interface ShotRecorderEventListener extends EventListener
{
	void event(ShotRecorderEvent ev);
}
