package com.siebentag.cj.queue;


public interface EventListener
{
	Class<?>[] register();
    void event(Event event);
}
