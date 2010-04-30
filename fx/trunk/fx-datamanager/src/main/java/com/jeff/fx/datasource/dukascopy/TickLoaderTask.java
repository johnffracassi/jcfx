package com.jeff.fx.datasource.dukascopy;

import java.util.List;

import com.jeff.fx.common.TickDataPoint;
import com.sun.jmx.snmp.tasks.Task;

public class TickLoaderTask
{
    public List<TickDataPoint> perform(Task task) throws Exception
    {
		List<TickDataPoint> data = (List<TickDataPoint>)task.getResult();
		
		try {
			TickDataDAO.save(data, getJobId());
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return data;
    }
}
