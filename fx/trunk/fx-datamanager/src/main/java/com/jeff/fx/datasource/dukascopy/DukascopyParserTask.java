package com.jeff.fx.datasource.dukascopy;

import java.util.List;

import com.jeff.fx.common.TickDataPoint;

public class DukascopyParserTask 
{
    public List<TickDataPoint> perform() throws Exception
    {
		List<TickDataPoint> ticks = DukascopyBinaryTickReader.parseBytes(data.getContent(), data.getFilename().substring(0, 6));
		return ticks;
    }
}
