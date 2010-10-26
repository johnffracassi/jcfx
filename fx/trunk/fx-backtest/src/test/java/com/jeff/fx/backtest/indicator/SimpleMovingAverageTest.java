package com.jeff.fx.backtest.indicator;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.indicator.SimpleMovingAverage;

import static org.junit.Assert.*;

public class SimpleMovingAverageTest 
{
	private SimpleMovingAverage sma;
	
	private static final float ERR = 0.00001f;
	
	@Test
	public void sma1Test()
	{
		CandleCollection cc = createConstantCandles();
		sma = new SimpleMovingAverage(1, CandleValueModel.BuyOpen);
		sma.calculate(cc);
		
		assertEquals("sma", sma.getKey());
		assertEquals(cc.getCandleCount(), sma.getSize());
		assertEquals(0.8950, sma.getValue(0), ERR);
		assertEquals(0.9000, sma.getValue(1), ERR);
		assertEquals(0.9000, sma.getValue(sma.getSize() - 1), ERR);
	}
	
	@Test
	public void sma2Test()
	{
		CandleCollection cc = createConstantCandles();
		sma = new SimpleMovingAverage(2, CandleValueModel.BuyOpen);
		sma.calculate(cc);
		
		assertEquals("sma", sma.getKey());
		assertEquals(cc.getCandleCount(), sma.getSize());
		assertEquals(0.8950, sma.getValue(0), ERR);
		assertEquals(0.8975, sma.getValue(1), ERR);
		assertEquals(0.9000, sma.getValue(2), ERR);
		assertEquals(0.9000, sma.getValue(sma.getSize() - 1), ERR);
	}
	
	@Test
	public void sma5Test()
	{
		CandleCollection cc = createConstantCandles();
		sma = new SimpleMovingAverage(5, CandleValueModel.BuyOpen);
		sma.calculate(cc);
		
		assertEquals("sma", sma.getKey());
		assertEquals(cc.getCandleCount(), sma.getSize());
		assertEquals(0.89500, sma.getValue(0), ERR);
		assertEquals(0.89750, sma.getValue(1), ERR);
		assertEquals(0.89833, sma.getValue(2), ERR);
		assertEquals(0.89875, sma.getValue(3), ERR);
		assertEquals(0.89900, sma.getValue(4), ERR);
		assertEquals(0.90000, sma.getValue(5), ERR);
		assertEquals(0.90000, sma.getValue(sma.getSize() - 1), ERR);
	}
	
	@Test
	public void sma2Increasing()
	{
		CandleCollection cc = createLinearCandles(0.0001f);
		sma = new SimpleMovingAverage(2, CandleValueModel.BuyOpen);
		sma.calculate(cc);
		
		assertEquals("sma", sma.getKey());
		assertEquals(cc.getCandleCount(), sma.getSize());
		assertEquals(0.90000, sma.getValue(0), ERR);
		assertEquals(0.90005, sma.getValue(1), ERR);
		assertEquals(0.90015, sma.getValue(2), ERR);
		assertEquals(0.90995, sma.getValue(100), ERR);
	}

	@Test
	public void sma100Increasing()
	{
		CandleCollection cc = createLinearCandles(0.0001f);
		sma = new SimpleMovingAverage(100, CandleValueModel.BuyOpen);
		sma.calculate(cc);
		
		assertEquals("sma", sma.getKey());
		assertEquals(cc.getCandleCount(), sma.getSize());
		assertEquals(0.90000, sma.getValue(0), ERR);
		assertEquals(0.90005, sma.getValue(1), ERR);
		assertEquals(0.90010, sma.getValue(2), ERR);
		assertEquals(0.90020, sma.getValue(4), ERR);
		assertEquals(0.90040, sma.getValue(8), ERR);
		assertEquals(0.90080, sma.getValue(16), ERR);
		assertEquals(0.90160, sma.getValue(32), ERR);
		assertEquals(0.90320, sma.getValue(64), ERR);
		assertEquals(0.90505, sma.getValue(100), ERR);
		assertEquals(0.91505, sma.getValue(200), ERR);
	}

	public CandleCollection createConstantCandles()
	{
		CandleWeek cw = new CandleWeek(new LocalDate(2010, 10, 26), FXDataSource.Forexite, Instrument.AUDUSD, Period.FifteenMin);
		
		CandleDataPoint candle = cw.getCandle(0);
		candle.setPrice(0.8950);
		cw.setCandle(candle);

		for(int i=1; i<cw.getCandleCount(); i++)
		{
			candle = cw.getCandle(i);
			candle.setPrice(0.9000);
			cw.setCandle(candle);
		}
		
		return new CandleCollection(cw);
	}

	public CandleCollection createLinearCandles(float increment)
	{
		CandleWeek cw = new CandleWeek(new LocalDate(2010, 10, 26), FXDataSource.Forexite, Instrument.AUDUSD, Period.FifteenMin);
		
		for(int i=0; i<cw.getCandleCount(); i++)
		{
			CandleDataPoint candle = cw.getCandle(i);
			candle.setPrice(0.9000 + (increment * i));
			cw.setCandle(candle);
		}
		
		return new CandleCollection(cw);
	}
}
