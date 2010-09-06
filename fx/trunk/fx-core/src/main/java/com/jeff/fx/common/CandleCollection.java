package com.jeff.fx.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Weeks;

import com.jeff.fx.util.DateUtil;

public class CandleCollection {

	private List<CandleWeek> weeks;
	private LocalDateTime start;
	private LocalDateTime end;
	private Period period;
	private int periodsInWeek;
	private float highPrice = Float.MIN_VALUE;
	private float lowPrice = Float.MAX_VALUE;
	
	public CandleCollection() {
		weeks = new ArrayList<CandleWeek>();
	}
	
	public CandleCollection(CandleWeek cw) {
		
		this();
		
		putCandleWeek(cw);
		period = cw.getPeriod();
		periodsInWeek = cw.getCandleCount();
	}
	
	public float[] getRawValues(CandleValueModel model) {
		float[] data = new float[getCandleCount()];
		for(int i=0; i<data.length; i++) {
			data[i] = getPrice(i, model);
		}
		return data;
	}
	
	public float[] getRawValues(int price) {
		
		float[] data = new float[getCandleCount()];
		
		for(int i=0; i<weeks.size(); i++) {
			CandleWeek week = weeks.get(i);
			for(int j=0; j<week.getRawValues(price).length; j++) {
				data[i * periodsInWeek + j] = week.getRawValues(price)[j];				                                                       
			}
		}
		
		return data;		
	}
	
	public Date[] getRawCandleDates() {

		Date[] dates = new Date[getCandleCount()];
		for(int i=0; i<weeks.size(); i++) {
			CandleWeek cw = weeks.get(i);
			LocalDateTime ldt = cw.getCandle(0).getDate();
			for(int j=0; j<periodsInWeek; j++) {
				dates[i * periodsInWeek + j] = ldt.plusMillis((int)(j * period.getInterval())).toDateTime().toDate();
			}
		}
		return dates;		
	}
	
	public double[] getRawValuesAsDouble(int price) {
		
		double[] data = new double[getCandleCount()];
		
		for(int i=0; i<weeks.size(); i++) {
			CandleWeek week = weeks.get(i);
			for(int j=0; j<week.getRawValues(price).length; j++) {
				data[i * periodsInWeek + j] = week.getRawValues(price)[j];				                                                       
			}
		}
		
		return data;		
	}
	
	private int getWeekIdx(int idx) {
		return idx / periodsInWeek;
	}
	
	private int getIdxInWeek(int idx) {
		return idx % periodsInWeek;
	}
	
	public float getPrice(int idx, CandleValueModel model) {
		return getCandleWeek(getWeekIdx(idx)).getPrice(getIdxInWeek(idx), model);
	}
	
	public CandleDataPoint getCandle(int idx) {
		return getCandleWeek(getWeekIdx(idx)).getCandle(getIdxInWeek(idx));
	}
	
	public CandleWeek getCandleWeek(LocalDate date) {
		int days = Days.daysBetween(start, date).getDays();
		int week = days / 7;
		return weeks.get(week);
	}
	
	public void putCandleWeek(CandleWeek cw) {
		
		weeks.add(cw);
		
		if(start == null || cw.getOpenDateTime().toLocalDate().isBefore(start)) {
			start = cw.getOpenDateTime();
		}
		
		if(end == null || cw.getCloseDateTime().toLocalDate().isAfter(end)) {
			end = cw.getCloseDateTime();
		}
		
		if(period == null) {
			period = cw.getPeriod();
			periodsInWeek = cw.getCandleCount();
		}
		
		if(cw.getHighPrice() > highPrice) {
			highPrice = cw.getHighPrice();
		}
		if(cw.getLowPrice() < lowPrice) {
			lowPrice = cw.getLowPrice();
		}
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public int getCandleCount() {
		int count = 0;
		for(CandleWeek cw : weeks) {
			count += cw.getCandleCount();
		}
		return count;
	}

	public int getWeekCount() {
		return weeks.size();
	}

	public CandleWeek getCandleWeek(int week) {
		return weeks.get(week);
	}

	public float getHighPrice() {
		return highPrice;
	}

	public float getLowPrice() {
		return lowPrice;
	}

	public int getCandleIndex(LocalDateTime ldt) {
		int weekIdx = Weeks.weeksBetween(start, ldt).getWeeks();
		int candleIdx = getCandleWeek(weekIdx).getCandleIndex(ldt);
		return weekIdx * periodsInWeek + candleIdx;
	}
}
