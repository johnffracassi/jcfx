package com.jeff.fx.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class CandleWeek implements Serializable {
	
	private static Logger log = Logger.getLogger(CandleWeek.class);
	private static final long serialVersionUID = 12416234512345l;

	private LocalDate date;
	private FXDataSource dataSource;
	private Instrument instrument;
	private Period period;
	private float[][] buy;
	private float[][] sell;
	private int[][] volumes;
	
	private boolean complete;
	private int startIdx;
	private int endIdx;
	
	private static final int OPEN = 0;
	private static final int HIGH = 1;
	private static final int LOW = 2;
	private static final int CLOSE = 3;
	
	public CandleWeek(LocalDate date, FXDataSource dataSource, Instrument instrument, Period period) {
		
		this.date = date;
		this.dataSource = dataSource;
		this.instrument = instrument;
		this.period = period;
	
		complete = false;
		startIdx = dataSource.getCalendar().getOpenTime().periodOfWeek(period);
		endIdx = dataSource.getCalendar().getCloseTime().periodOfWeek(period);
		
		int periodsInWeek = endIdx - startIdx;
		buy = new float[4][periodsInWeek];
		sell = new float[4][periodsInWeek];
		volumes = new int[2][periodsInWeek];
	}
	
	public void fillGaps() {
		
		// fill in the gaps
		for(int i=1; i<buy[OPEN].length-1; i++) {
			
			// if we have an empty candle...
			if(buy[OPEN][i] == 0.0) {
				if(!isEmptyCandle(i-1) && !isEmptyCandle(i+1)) {
					// if there are 2 populated surrounding candles
					merge(i, i-1, i+1);
				} else if(!isEmptyCandle(i-1) && isEmptyCandle(i+1) && checkGapSize(i) < 5) {
					// copy forward (up to 5 candles) if the following candle is also null
					copyForward(i, i-1);
				}
			}
		}
	}
	
	private void merge(int dest, int srcOpen, int srcClose) {
		buy[OPEN][dest] = buy[CLOSE][srcOpen];
		buy[CLOSE][dest] = buy[OPEN][srcClose];
		buy[HIGH][dest] = Math.max(buy[OPEN][dest], buy[CLOSE][dest]);
		buy[LOW][dest] = Math.min(buy[OPEN][dest], buy[CLOSE][dest]);
		sell[OPEN][dest] = sell[CLOSE][srcOpen];
		sell[CLOSE][dest] = sell[OPEN][srcClose];
		sell[HIGH][dest] = Math.max(sell[OPEN][dest], sell[CLOSE][dest]);
		sell[LOW][dest] = Math.min(sell[OPEN][dest], sell[CLOSE][dest]);
		volumes[0][dest] = 0;
		volumes[1][dest] = 0;
	}
	
	private void copyForward(int dest, int src) {
		buy[OPEN][dest] = buy[CLOSE][src];
		buy[CLOSE][dest] = buy[OPEN][dest];
		buy[HIGH][dest] = buy[OPEN][dest];
		buy[LOW][dest] = buy[OPEN][dest];
		sell[OPEN][dest] = sell[CLOSE][src];
		sell[CLOSE][dest] = sell[OPEN][dest];
		sell[HIGH][dest] = sell[OPEN][dest];
		sell[LOW][dest] = sell[OPEN][dest];
		volumes[0][dest] = 0;
		volumes[1][dest] = 0;
	}
	
	private int checkGapSize(int idx) {
		if(!isEmptyCandle(idx)) {
			return 0;
		} else {
			int count = 0;
			while(isEmptyCandle(idx) && idx<buy[OPEN].length) {
				idx++;
				count++;
			}
			return count;
		}
	}
	
	private boolean isEmptyCandle(int idx) {
		if(idx < 0 || idx >= buy[OPEN].length)
			return true;
		else 
			return (buy[OPEN][idx] == 0);
	}
	
	public CandleDataPoint findNextLowBelowPrice(TimeOfWeek from, TimeOfWeek to, float targetPrice, OfferSide offerSide) {
		
		int startIdx = from.periodOfWeek(this.period) - this.startIdx;
		int endIdx = (to == null ? buy[LOW].length : (to.periodOfWeek(this.period) - this.startIdx));
		
		for(int idx=startIdx; idx<endIdx; idx++) {
			float price = (offerSide == OfferSide.Ask) ? sell[LOW][idx] : buy[LOW][idx];
			if(price <= targetPrice) {
				return getCandle(idx);
			}
		}
		
		return null;
	}
	
	public CandleDataPoint findNextHighAbovePrice(TimeOfWeek from, TimeOfWeek to, float targetPrice, OfferSide offerSide) {
		
		int startIdx = from.periodOfWeek(this.period) - this.startIdx;
		int endIdx = (to == null ? buy[LOW].length : (to.periodOfWeek(this.period) - this.startIdx));
		
		for(int idx=startIdx; idx<endIdx; idx++) {
			float price = (offerSide == OfferSide.Ask) ? sell[HIGH][idx] : buy[HIGH][idx];
			if(price >= targetPrice) {
				return getCandle(idx);
			}
		}
		
		return null;
	}
	
	/**
	 * Merge candles down a period (eg/ merge m1 candles to m15 candles)
	 * @param source
	 * @param target
	 */
	public CandleWeek(CandleWeek source, Period target) {
		
		// copy details from source candle week, but with targeted period
		this(source.getDate(), source.getDataSource(), source.getInstrument(), target);
		
		// how many source periods in 1 of the target periods
		int ratio = (int)(target.getInterval() / source.getPeriod().getInterval());
		
		// perform the candle merging
		for(int destIdx=0; destIdx<getCandleCount(); destIdx++) {
			
			// map the destination index to the source index
			int srcIdx = destIdx * ratio;
			int srcEndIdx = Math.min(srcIdx + ratio - 1, buy[OPEN].length - 1);
			
			// get the new open and close values
			buy[OPEN][destIdx] = source.buy[OPEN][srcIdx];
			sell[OPEN][destIdx] = source.sell[OPEN][srcIdx];
			buy[CLOSE][destIdx] = source.buy[CLOSE][srcEndIdx];
			sell[CLOSE][destIdx] = source.sell[CLOSE][srcEndIdx];
			
			// find the high and lows
			for(int idx=srcIdx; idx<srcEndIdx; idx++) {

				if(source.buy[HIGH][idx] > buy[HIGH][destIdx])
					buy[HIGH][destIdx] = source.buy[HIGH][idx];
				if(source.sell[HIGH][idx] > sell[HIGH][destIdx])
					sell[HIGH][destIdx] = source.sell[HIGH][idx];
				
				if(source.buy[LOW][idx] < buy[LOW][destIdx] || buy[LOW][destIdx] == 0.0)
					buy[LOW][destIdx] = source.buy[LOW][idx];
				if(source.sell[LOW][idx] < sell[LOW][destIdx] || sell[LOW][destIdx] == 0.0)
					sell[LOW][destIdx] = source.sell[LOW][idx];
				
				// TODO add candle volumes
			}
		}
	}
	
	/**
	 * Get the candle opening at, or containing, the specified time
	 * @param time
	 * @return
	 */
	public CandleDataPoint getCandle(TimeOfWeek time) {
		
		int idx = time.periodOfWeek(period) - startIdx;
		
		if(isEmptyCandle(idx)) {
			return null;
		} else {
			return getCandle(idx);
		}
	}
	
	public void setCandle(CandleDataPoint candle) {
		
		TimeOfWeek time = new TimeOfWeek(candle.getDate());
		int idx = time.periodOfWeek(candle.getPeriod()) - startIdx;

		if(idx < 0 || idx > endIdx - startIdx || idx >= buy[0].length) {
			log.error("INVALID: #" + idx + "/" + buy[0].length + " = " + candle);
		} else {
			buy[0][idx] = (float)candle.getBuyOpen();
			buy[1][idx] = (float)candle.getBuyHigh();
			buy[2][idx] = (float)candle.getBuyLow();
			buy[3][idx] = (float)candle.getBuyClose();
			sell[0][idx] = (float)candle.getSellOpen();
			sell[1][idx] = (float)candle.getSellHigh();
			sell[2][idx] = (float)candle.getSellLow();
			sell[3][idx] = (float)candle.getSellClose();
			volumes[0][idx] = (int)candle.getBuyVolume();
			volumes[1][idx] = (int)candle.getSellVolume();
		}
	}
	
	/**
	 * 
	 * @param idx zero index is the first candle in the collection (not candle at sunday midnight)
	 * @return
	 */
	public CandleDataPoint getCandle(int idx) {
		
		CandleDataPoint candle = new CandleDataPoint();
		
		candle.setDataSource(dataSource);
		candle.setInstrument(instrument);
		candle.setPeriod(period);
		candle.setBuyOpen(buy[0][idx]);
		candle.setBuyHigh(buy[1][idx]);
		candle.setBuyLow(buy[2][idx]);
		candle.setBuyClose(buy[3][idx]);
		candle.setSellOpen(sell[0][idx]);
		candle.setSellHigh(sell[1][idx]);
		candle.setSellLow(sell[2][idx]);
		candle.setSellClose(sell[3][idx]);
		candle.setBuyVolume(volumes[0][idx]);
		candle.setSellVolume(volumes[1][idx]);
		candle.setTickCount(0);
		
		LocalDateTime dateTime = new LocalDateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0, 0);
		candle.setDateTime(dateTime.plusMillis((int)((startIdx + idx) * period.getInterval())));
		
		return candle;
	}
	
	public int getCandleCount() {
		return buy[0].length;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public LocalDate getStartDate() {
		return date;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public FXDataSource getDataSource() {
		return dataSource;
	}

	public Instrument getInstrument() {
		return instrument;
	}
	
	public Period getPeriod() {
		return period;
	}
}
