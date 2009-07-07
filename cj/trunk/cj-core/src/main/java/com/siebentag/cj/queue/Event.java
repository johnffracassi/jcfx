package com.siebentag.cj.queue;

import com.siebentag.cj.game.action.Action;

public interface Event extends QueueItem
{
	Action getParent();
}