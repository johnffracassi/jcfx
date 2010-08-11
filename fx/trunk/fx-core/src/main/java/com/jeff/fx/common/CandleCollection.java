package com.jeff.fx.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.LocalDate;

import com.jeff.fx.util.DateUtil;

public class CandleCollection {

	private Map<LocalDate, CandleWeek> weeks;
	private LocalDate start;
	private LocalDate end;
	
	public CandleCollection() {
		weeks = new LinkedHashMap<LocalDate, CandleWeek>();
	}

	public CandleCollection(CandleWeek cw) {
		this();
		putCandleWeek(cw);
	}
	
	public CandleWeek getCandleWeek(LocalDate date) {
		
		LocalDate startDate = DateUtil.getStartOfWeek(date);
		return weeks.get(startDate);
	}
	
	public void putCandleWeek(CandleWeek cw) {
		
		LocalDate weekStart = DateUtil.getStartOfWeek(cw.getStartDate());
		weeks.put(weekStart, cw);
		
		if(start == null || weekStart.isBefore(start)) {
			start = weekStart;
		}
		
		if(end == null || weekStart.plusDays(7).isAfter(end)) {
			end = weekStart.plusDays(7);
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
		for(CandleWeek cw : weeks.values()) {
			count += cw.getCandleCount();
		}
		return count;
	}

	public int getWeekCount() {
		return weeks.size();
	}

	public CandleWeek getCandleWeek(int currentWeek) {
		return weeks.get(start.plusDays(7*currentWeek));
	}
}
