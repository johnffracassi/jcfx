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
	public void testIndexing() {

		CandleCollection cc = buildCollection();
		
		assertEquals(dateTime, cc.getCandleWeek(0).getCandle(0).getDateTime());
		assertEquals(dateTime.plusWeeks(1), cc.getCandleWeek(1).getCandle(0).getDateTime());
		assertEquals(dateTime.plusWeeks(2), cc.getCandleWeek(2).getCandle(0).getDateTime());
		
		assertEquals(dateTime, cc.getCandle(0).getDateTime());
		assertEquals(dateTime.plusWeeks(1), cc.getCandle(7140).getDateTime());
		assertEquals(dateTime.plusWeeks(2), cc.getCandle(14280).getDateTime());
		
		assertEquals(dateTime.plusWeeks(1), cc.getCandleWeek(date.plusWeeks(1)).getCandle(0).getDateTime());
		assertEquals(dateTime.plusWeeks(1), cc.getCandleWeek(dateTime.plusWeeks(1)).getCandle(0).getDateTime());
	}
	
	@Test
	public void testRawData() {
		
		CandleCollection cc = buildCollection();
		
		assertEquals(dateTime.plusWeeks(1).toDateTime().toDate(), cc.getRawCandleDates()[7140]);
		assertEquals(0.8000, cc.getRawValues(CandleValueModel.BuyOpen)[0], TOLERANCE);
		assertEquals(0.8000, cc.getRawValues(0)[0], TOLERANCE);
		assertEquals(0.8000, cc.getRawValuesAsDouble(0)[0], TOLERANCE); // open values
		
	}
	
	@Test
	public void testAddingWeeks() {
		
		CandleCollection cc = new CandleCollection();
		cc.putCandleWeek(buildData(date.plusDays(2)));
		assertTrue(cc.getStart().equals(dateTime));
		assertEquals(dateTime.plusDays(5).minusHours(1), cc.getEnd());
		assertEquals(7140, cc.getCandleCount());
		assertEquals(1, cc.getWeekCount());

		cc.putCandleWeek(buildData(date.plusDays(9)));
		assertTrue(cc.getStart().equals(dateTime));
		assertEquals(dateTime.plusDays(12).minusHours(1), cc.getEnd());
		assertEquals(14280, cc.getCandleCount());
		assertEquals(2, cc.getWeekCount());
		
		assertEquals(0, cc.getCandleIndex(new LocalDateTime(2010, 9, 5, 22, 00, 00)));
		assertEquals(30, cc.getCandleIndex(new LocalDateTime(2010, 9, 5, 22, 30, 00)));
		assertEquals(7140, cc.getCandleIndex(new LocalDateTime(2010, 9, 12, 22, 00, 00)));
		assertEquals(14279, cc.getCandleIndex(new LocalDateTime(2010, 9, 17, 20, 59, 00)));

	}
	
	private CandleCollection buildCollection() {
		CandleCollection cc = new CandleCollection();
		cc.putCandleWeek(buildData(date.plusDays(1)));
		cc.putCandleWeek(buildData(date.plusDays(8)));
		cc.putCandleWeek(buildData(date.plusDays(15)));
		cc.putCandleWeek(buildData(date.plusDays(22)));
		return cc;
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
