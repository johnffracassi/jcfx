package com.jeff.fx.common;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;


public class CandleWeekTest {
	
	@Test
	public void testCandleIndex() {
		CandleWeek cw = new CandleWeek(new LocalDate(2010, 9, 7), FXDataSource.Forexite, Instrument.AUDUSD, Period.OneMin);
		assertTrue("opening candle idx=" + cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getOpenTime()), cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getOpenTime()) == 0);
		assertTrue("closing candle idx=" + cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getCloseTime()), cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getCloseTime()) == 7140);
		assertTrue(cw.getCandleIndex(new LocalDateTime(2010, 9, 5, 22, 0, 0)) == 0);
		assertTrue(cw.getCandleIndex(new LocalDateTime(2010, 9, 6, 22, 0, 0)) == 1440);
	}
	
	@Test
	public void testGetSetCandle() {
		CandleWeek cw = buildData();
		assertEquals(cw.getCandle(0).getBuyOpen(), 0.8000, 0.0000001);
		assertEquals(cw.getCandle(10).getBuyOpen(), 0.8001, 0.0000001);
	}
	
	@Test
	public void testHighLow() {
		CandleWeek cw = buildData();
		assertEquals(cw.getLowPrice(), 0.80000, 0.0000001);
		assertEquals(cw.getHighPrice(), 0.87139, 0.0000001);
	}
	
	private CandleWeek buildData() {
		CandleWeek cw = new CandleWeek(new LocalDate(2010, 9, 7), FXDataSource.Forexite, Instrument.AUDUSD, Period.OneMin);
		
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
