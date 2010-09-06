package com.jeff.fx.common;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import static org.junit.Assert.*;
import org.junit.Test;

public class CandleCollectionTest {

	private LocalDateTime lastMinute = new LocalDateTime(2010, 9, 10, 20, 59, 0);
	private LocalDateTime dateTime = new LocalDateTime(2010, 9, 5, 22, 00, 00);
	private LocalDate date = dateTime.toLocalDate();
	private LocalTime time = dateTime.toLocalTime();
	private static final double TOLERANCE = 0.0000001;

	@Test
	public void test() {
		
		CandleCollection cc = new CandleCollection();
		cc.putCandleWeek(buildData(date.plusDays(2)));
		assertTrue(cc.getStart().equals(date));
		assertEquals(date.plusDays(5), cc.getEnd());
		assertEquals(7140, cc.getCandleCount());
		assertEquals(1, cc.getWeekCount());

		cc.putCandleWeek(buildData(date.plusDays(9)));
		assertTrue(cc.getStart().equals(date));
		assertEquals(date.plusDays(12), cc.getEnd());
		assertEquals(14280, cc.getCandleCount());
		assertEquals(2, cc.getWeekCount());
		
		assertEquals(0, cc.getCandleIndex(new LocalDateTime(2010, 9, 5, 22, 00, 00)));

	}
	
	private CandleWeek buildData(LocalDate date) {
		CandleWeek cw = new CandleWeek(date, FXDataSource.Forexite, Instrument.AUDUSD, Period.OneMin);
		
		for(int i=0; i<7140; i++) {
			CandleDataPoint candle = new CandleDataPoint(0.8000 + (i / 100000.0));
			candle.setDataSource(FXDataSource.Forexite);
			candle.setInstrument(Instrument.AUDUSD);
			candle.setPeriod(Period.OneMin);
			candle.setDateTime(cw.getOpenDateTime().plusMinutes(i));
			cw.setCandle(candle);
		}

		return cw;
	}
}
