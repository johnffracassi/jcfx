package com.siebentag.fx.source.dukascopy;


public abstract class AbstractTask<T> implements Task<T>
{
	private int jobId;
	private TaskStatusListener listener;
	private TaskStatus status = TaskStatus.New;
	private T result;
	private Task<?> nextTask = null;
	
	public abstract T perform(Task task) throws Exception;

	public void execute(Task previousTask)
    {
		setStatus(TaskStatus.Running);
		
		try
		{
			setResult(perform(previousTask));
			setStatus(TaskStatus.Complete);
			executeNextTask();
		}
		catch(Exception ex)
		{
			setFailed(ex);
		}
    }
	
	public void executeNextTask()
	{
		if(nextTask != null)
		{
			nextTask.execute(this);
		}
	}
	
	public T getResult()
    {
	    return result;
    }

	public void setResult(T result)
	{
		this.result = result;
	}
	
	public TaskStatus getStatus()
    {
	    return status;
    }
	
	public void setStatus(TaskStatus status)
	{
		this.status = status;

		if(listener != null)
		{
			switch(status)
			{
				case Complete: listener.taskCompleted(this); break;
				case Running: listener.taskStarted(this); break;
			}
		}
	}

	public void setFailed(Exception ex)
	{
		this.status = TaskStatus.Failed;
		
		if(listener != null)
		{
			listener.taskFailed(this, ex);
		}
	}

	public Task getNextTask()
	{
		return nextTask;
	}
	
	public void setNextTask(Task task)
    {
		this.nextTask = task;
    }
	
	public void setStatusListener(TaskStatusListener listener)
    {
		this.listener = listener;
    }

	public int getJobId()
    {
    	return jobId;
    }

	public void setJobId(int jobId)
    {
    	this.jobId = jobId;
    }
}
