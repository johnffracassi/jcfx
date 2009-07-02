package com.siebentag.fx.source.dukascopy;

import java.util.List;

import com.siebentag.fx.TickDataPoint;

public class DukascopyParserTask extends AbstractTask<List<TickDataPoint>>
{
	public DukascopyParserTask(Task task)
	{
		setNextTask(task);
	}
	
	@Override
    public List<TickDataPoint> perform(Task task) throws Exception
    {
		FileData data = (FileData)task.getResult();

		System.out.println("DukascopyParserTask: Parsing data from " + data.getFilename());
		
		List<TickDataPoint> ticks = DukascopyBinaryTickReader.parseBytes(data.getContent(), data.getFilename().substring(0, 6));
		return ticks;
    }
}
