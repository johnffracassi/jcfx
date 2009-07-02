package com.siebentag.fx.source.dukascopy;

public interface TaskStatusListener
{
	void taskStarted(Task task);
	void taskCompleted(Task task);
	void taskFailed(Task task, Exception ex);
}
