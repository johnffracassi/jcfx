package com.siebentag.fx.source.dukascopy;

import java.util.List;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.collator.TickCollator;
import com.siebentag.fx.loader.CandleDataDAO;

public class CandleLoaderTask extends AbstractTask<List<TickDataPoint>>
{
	public CandleLoaderTask(Task task)
	{
		setNextTask(task);
	}
	
	@Override
    public List<TickDataPoint> perform(Task task) throws Exception
    {
		List<TickDataPoint> data = (List<TickDataPoint>)task.getResult();
		
		System.out.println("CandleLoaderTask (1m)");
		List<CandleStickDataPoint> candles = TickCollator.process(data, 60);
		CandleDataDAO.load(candles);
		
		System.out.println("CandleLoaderTask (5m)");
		candles = TickCollator.process(data, 300);
		CandleDataDAO.load(candles);
		
		System.out.println("CandleLoaderTask (15m)");
		candles = TickCollator.process(data, 900);
		CandleDataDAO.load(candles);
		
		return data;
    }
}
