package com.siebentag.fx.source.dukascopy;

public interface Task<T>
{
	void execute(Task previousTask);
	T getResult();
	TaskStatus getStatus();
	void setNextTask(Task nextTask);
	void setStatusListener(TaskStatusListener listener);
}
