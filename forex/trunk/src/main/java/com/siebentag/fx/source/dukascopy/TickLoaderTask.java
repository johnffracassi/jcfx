package com.siebentag.fx.source.dukascopy;

import java.util.List;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.loader.TickDataDAO;

public class TickLoaderTask extends AbstractTask<List<TickDataPoint>>
{
	public TickLoaderTask(Task task)
	{
		setNextTask(task);
	}
	
	@Override
    public List<TickDataPoint> perform(Task task) throws Exception
    {
		List<TickDataPoint> data = (List<TickDataPoint>)task.getResult();
		
		System.out.println("TickLoaderTask: Loading " + data.size() + " records to DB");

		try
		{
			TickDataDAO.save(data, getJobId());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		StatsPanel.addRecords(data.size());
		
		return data;
    }
}
