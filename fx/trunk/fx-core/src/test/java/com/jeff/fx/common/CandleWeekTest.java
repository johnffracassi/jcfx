package com.jeff.fx.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class CandleWeekTest {
	
	private LocalDateTime lastMinute = new LocalDateTime(2010, 9, 10, 20, 59, 0);
	private LocalDateTime dateTime = new LocalDateTime(2010, 9, 5, 22, 00, 00);
	private LocalDate date = dateTime.toLocalDate();
	private LocalTime time = dateTime.toLocalTime();
	private static final double TOLERANCE = 0.0000001;
	
	@Test
	public void testCandleIndex() {
		CandleWeek cw = new CandleWeek(new LocalDate(2010, 9, 7), FXDataSource.Forexite, Instrument.AUDUSD, Period.OneMin);
		assertTrue("opening candle idx=" + cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getOpenTime()), cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getOpenTime()) == 0);
		assertTrue("closing candle idx=" + cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getCloseTime()), cw.getCandleIndex(FXDataSource.Forexite.getCalendar().getCloseTime()) == 7140);
		assertTrue(cw.getCandleIndex(dateTime) == 0);
		assertTrue(cw.getCandleIndex(dateTime.plusDays(1)) == 1440);
	}
	
	@Test
	public void testGetSetCandle() {
		CandleWeek cw = buildData();
		assertEquals(cw.getCandle(0).getBuyOpen(), 0.8000, TOLERANCE);
		assertEquals(cw.getCandle(10).getBuyOpen(), 0.8001, TOLERANCE);
	}
	
	@Test
	public void testHighLow() {
		CandleWeek cw = buildData();
		assertEquals(cw.getLowPrice(), 0.80000, TOLERANCE);
		assertEquals(cw.getHighPrice(), 0.87139, TOLERANCE);
	}
	
	@Test
	public void testMerging() {
		CandleWeek cw1 = buildData();
		CandleWeek cw30 = new CandleWeek(cw1, Period.ThirtyMin);
		
		assertEquals(7140 / 30, cw30.getCandleCount());
		assertEquals(0, cw30.getCandleIndex(dateTime));
		assertEquals(1, cw30.getCandleIndex(dateTime.plusMinutes(30)));
		assertEquals(1, cw30.getCandleIndex(dateTime.plusMinutes(35)));
		assertEquals(237, cw30.getCandleIndex(dateTime.plusMinutes(7115)));
		
		assertEquals(238, cw30.getCandleIndex(FXDataSource.Forexite.getCalendar().getCloseTime()));

		for(int i=0; i<238; i++) {
			CandleDataPoint candle = cw30.getCandle(i);
			assertEquals(0.8000 + (i * 0.0003), candle.getBuyOpen(), TOLERANCE);
		}
	}
	
	@Test
	public void highsAndLows() {
		CandleWeek cw30 = new CandleWeek(buildData(), Period.ThirtyMin);
		
		CandleDataPoint candle = cw30.findNextHighAbovePrice(new TimeOfWeek(0, 22, 00), new TimeOfWeek(5, 21, 00), 0.8100f, OfferSide.Buy);
		assertTrue(candle != null);
		assertTrue(0.8100 + " < " + candle.getBuyHigh(), 0.8100 < candle.getBuyHigh());
		
		candle = cw30.findNextLowBelowPrice(new TimeOfWeek(0, 22, 00), new TimeOfWeek(5, 21, 00), 0.7999f, OfferSide.Buy);
		assertTrue(candle == null);
		
		cw30.getRawValues(0)[2] = 0.8003f;
		cw30.getRawValues(1)[2] = 0.8050f;
		cw30.getRawValues(2)[2] = 0.7950f;
		cw30.getRawValues(3)[2] = 0.8003f;
		assertEquals(0.8050, cw30.getCandle(2).getBuyHigh(), TOLERANCE);

		candle = cw30.findNextLowBelowPrice(new TimeOfWeek(0, 22, 00), new TimeOfWeek(5, 21, 00), 0.7995f, OfferSide.Sell);
		assertTrue(candle != null);
		assertTrue(0.7995 + " > " + candle.getBuyHigh(), candle.getBuyLow() < 0.7995);
	}
	
	@Test
	public void smallTests() {
		CandleWeek cw1 = buildData();
		CandleWeek cw30 = new CandleWeek(cw1, Period.ThirtyMin);
		
		assertEquals(0.8000, cw30.getPrice(0, CandleValueModel.BuyOpen), TOLERANCE);
		assertEquals(0.8003, cw30.getPrice(1, CandleValueModel.BuyOpen), TOLERANCE);
		assertEquals(0.8000, cw30.getRawValues(0)[0], TOLERANCE);
		assertEquals(0.8003, cw30.getCandle(new TimeOfWeek(0, 22, 30)).getBuyOpen(), TOLERANCE);
		assertTrue(cw30.isEmptyCandle(-1));
		assertTrue(cw30.isEmptyCandle(100000));
		assertTrue(!cw30.isEmptyCandle(0));
		assertTrue(!cw30.isEmptyCandle(5));
	}
	
	private CandleWeek buildData() {
		CandleWeek cw = new CandleWeek(date.plusDays(2), FXDataSource.Forexite, Instrument.AUDUSD, Period.OneMin);
		
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
