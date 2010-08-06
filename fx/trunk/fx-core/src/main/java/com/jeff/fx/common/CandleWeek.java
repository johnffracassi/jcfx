package com.jeff.fx.common;

import java.io.Serializable;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class CandleWeek implements Serializable {
	
	private static final long serialVersionUID = 100000001l;

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
	
	/**
	 * Get the candle opening at, or containing, the specified time
	 * @param time
	 * @return
	 */
	public CandleDataPoint getCandle(TimeOfWeek time) {
		
		int idx = time.periodOfWeek(period) - startIdx;
		return getCandle(idx);
	}
	
	public void setCandle(CandleDataPoint candle) {
		
		TimeOfWeek time = new TimeOfWeek(candle.getDate());
		int idx = time.periodOfWeek(candle.getPeriod()) - startIdx;

		if(idx < 0 || idx > endIdx - startIdx || idx >= buy[0].length) {
			System.out.println("INVALID: #" + idx + "/" + buy[0].length + " = " + candle);
		}
		
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
