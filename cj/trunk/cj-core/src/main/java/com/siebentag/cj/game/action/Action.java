package com.siebentag.cj.game.action;

import com.siebentag.cj.queue.QueueItem;

public interface Action extends QueueItem, Runnable
{
	void cancel();
	ActionState getState();
}

