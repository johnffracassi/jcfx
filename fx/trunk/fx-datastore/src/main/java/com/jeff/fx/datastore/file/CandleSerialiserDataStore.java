package com.jeff.fx.datastore.file;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

@Component
public class CandleSerialiserDataStore extends SerialiserDataStore<CandleDataPoint>
{
	private Locator locator;

	public static void main(String[] args)
	{
		CandleDataPoint p1 = new CandleDataPoint();
		p1.setDataSource(FXDataSource.GAIN);
		p1.setInstrument(Instrument.AUDUSD);
		p1.setDate(new LocalDateTime());
		p1.setPeriod(Period.FiveMin);
		
		CandleDataPoint p2 = new CandleDataPoint(p1);
		
		List<CandleDataPoint> list = new ArrayList<CandleDataPoint>();
		list.add(p1);
		list.add(p2);
		
		CandleSerialiserDataStore ds = new CandleSerialiserDataStore();
		
		try
		{
			ds.store(list);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			List<CandleDataPoint> candles = ds.load(FXDataSource.GAIN, Instrument.AUDUSD, new LocalDateTime(), Period.FiveMin);
			System.out.println(candles);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public CandleSerialiserDataStore()
	{
		locator = new Locator();
		locator.setExtension("ser");
	}
}
