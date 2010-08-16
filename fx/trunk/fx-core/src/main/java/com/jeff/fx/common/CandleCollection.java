package com.jeff.fx.common;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.jeff.fx.util.DateUtil;

public class CandleCollection {

	private List<CandleWeek> weeks;
	private LocalDate start;
	private LocalDate end;
	private Period period;
	private int periodsInWeek;
	
	public CandleCollection() {
		weeks = new ArrayList<CandleWeek>();
	}

	public CandleCollection(CandleWeek cw) {
		this();
		putCandleWeek(cw);
		period = cw.getPeriod();
		periodsInWeek = cw.getCandleCount();
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
		
		LocalDate weekStart = DateUtil.getStartOfWeek(cw.getStartDate());
		weeks.add(cw);
		
		if(start == null || weekStart.isBefore(start)) {
			start = weekStart;
		}
		
		if(end == null || weekStart.plusDays(7).isAfter(end)) {
			end = weekStart.plusDays(7);
		}
		
		if(period == null) {
			period = cw.getPeriod();
			periodsInWeek = cw.getCandleCount();
		}
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
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
}
