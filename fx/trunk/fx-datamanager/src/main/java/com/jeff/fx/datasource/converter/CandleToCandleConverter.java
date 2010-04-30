package com.jeff.fx.datasource.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public class CandleToCandleConverter {
	
	private static Logger log = Logger.getLogger(CandleToCandleConverter.class);
	
	public List<CandleDataPoint> convert(List<CandleDataPoint> originalCandles, Period targetPeriod) {
		
		if(originalCandles == null || targetPeriod == null || originalCandles.size() == 0) {
			return Collections.<CandleDataPoint>emptyList();
		}
		
		// order the list by date
		Collections.sort(originalCandles);
		
		// check all candles are of the same pedigree
		if(!checkHomogenous(originalCandles)) {
			throw new RuntimeException("Candle collection has more than one type of period/dataSource/instrument");
		}
		
		// how many old candles go into the new candle?
		Period sourcePeriod = originalCandles.get(0).getPeriod();
		int count = (int)(targetPeriod.getInterval() / sourcePeriod.getInterval());

		// create the new candles list
		int newCandleCount = (int)(originalCandles.size() / count);
		List<CandleDataPoint> newCandles = new ArrayList<CandleDataPoint>();

		// create each of the new candles
		for(int i=0; i<newCandleCount; i++) {
			CandleDataPoint newCandle = new CandleDataPoint(originalCandles.subList(i*count, (i+1)*count));
			newCandle.setPeriod(targetPeriod);
			newCandles.add(newCandle);
		}
		
		return newCandles;
	}
	
	private boolean checkHomogenous(List<CandleDataPoint> candles) {
		
		Period period = candles.get(0).getPeriod();
		FXDataSource dataSource = candles.get(0).getDataSource();
		Instrument instrument = candles.get(0).getInstrument();

		for(CandleDataPoint candle : candles) {
			if(!candle.getPeriod().equals(period))
				return false;
			
			if(!candle.getDataSource().equals(dataSource))
				return false;
			
			if(!candle.getInstrument().equals(instrument))
				return false;
		}
		
		return true;
	}
}
