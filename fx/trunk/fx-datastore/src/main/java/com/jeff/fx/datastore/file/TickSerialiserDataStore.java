package com.jeff.fx.datastore.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.TickDataPoint;

@Component
public class TickSerialiserDataStore extends SerialiserDataStore<TickDataPoint>
{
	private static Logger log = Logger.getLogger(TickSerialiserDataStore.class);
	
	private static DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");
	
	@Override
	public void store(List<TickDataPoint> data) throws Exception 
	{
		log.debug("storing " + data.size() + " ticks in data store, splitting into daily files");
		
		// split into hourly batches
		Map<String,List<TickDataPoint>> tickMap = new HashMap<String, List<TickDataPoint>>();
		
		for(TickDataPoint tick : data)
		{
			String key = df.print(tick.getDate());
			List<TickDataPoint> ticksForHour = tickMap.get(key);
			if(ticksForHour == null) 
			{
				log.debug("creating new store for " + key);
				ticksForHour = new ArrayList<TickDataPoint>(10000);
				tickMap.put(key, ticksForHour);
			}
			ticksForHour.add(tick);
		}
		
		// store each hourly batch in a different file
		for(Entry<String,List<TickDataPoint>> list : tickMap.entrySet())
		{
			super.store(list.getValue());
		}
	}
}
