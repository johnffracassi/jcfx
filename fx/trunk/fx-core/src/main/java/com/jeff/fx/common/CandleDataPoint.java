package com.jeff.fx.common;

import java.io.Serializable;
import java.util.List;

public class CandleDataPoint extends AbstractFXDataPoint implements
		Serializable {
	private static final long serialVersionUID = -1074171723478712229L;

	private Period period;

	private double buyOpen;
	private double buyHigh;
	private double buyLow;
	private double buyClose;

	private double sellOpen;
	private double sellHigh;
	private double sellLow;
	private double sellClose;

	private int tickCount;

	public CandleDataPoint() {
	}

	public CandleDataPoint(List<CandleDataPoint> candles) {
		this(candles.get(0));
		
		for(CandleDataPoint candle : candles) {
			if(candle.buyLow < buyLow) buyLow = candle.buyLow;
			if(candle.sellLow < sellLow) sellLow = candle.sellLow;
			
			if(candle.buyHigh > buyHigh) buyHigh = candle.buyHigh;
			if(candle.sellHigh > sellHigh) sellHigh = candle.sellHigh;
			
			buyClose = candle.buyClose;
			sellClose = candle.sellClose;
			
			tickCount ++;
		}
	}
	
	public CandleDataPoint(CandleDataPoint candle) {
		
		super(candle);

		this.period = candle.period;
		this.buyClose = candle.buyClose;
		this.buyHigh = candle.buyHigh;
		this.buyLow = candle.buyLow;
		this.buyOpen = candle.buyOpen;
		this.sellClose = candle.sellClose;
		this.sellHigh = candle.sellHigh;
		this.sellLow = candle.sellLow;
		this.sellOpen = candle.sellOpen;
		this.tickCount = candle.tickCount;
	}

	public CandleDataPoint(double value) {
		this.buyClose = value;
		this.buyHigh = value;
		this.buyLow = value;
		this.buyOpen = value;
		this.sellClose = value;
		this.sellHigh = value;
		this.sellLow = value;
		this.sellOpen = value;
	}

	@Override
	public String toString() {
		if(tickCount > 0) {
			return String.format("[%s/%s/%s] %s / %.4f %.4f %.4f %.4f (%d ticks)", getInstrument(), getDataSource(), period.key, getDate(), sellOpen, sellHigh, sellLow, sellClose, tickCount);
		} else {
			return String.format("[%s/%s/%s] %s / %.4f %.4f %.4f %.4f", getInstrument(), getDataSource(), period.key, getDate(), sellOpen, sellHigh, sellLow, sellClose);
		}
	}

	public double evaluate(CandleValueModel model) {
		return model.evaluate(this);
	}

	public void merge(CandleDataPoint newClose) {
		buyClose = newClose.buyClose;
		sellClose = newClose.sellClose;

		if (newClose.buyHigh > buyHigh)
			buyHigh = newClose.buyHigh;
		if (newClose.sellHigh > sellHigh)
			sellHigh = newClose.sellHigh;

		if (newClose.buyLow < buyLow)
			buyLow = newClose.buyLow;
		if (newClose.sellLow < sellLow)
			sellLow = newClose.sellLow;

		tickCount += newClose.tickCount;
	}

	/**
	 * Convert the candle to a tick (based on opening price)
	 * 
	 * @return
	 */
	public TickDataPoint toTick() {
		TickDataPoint tick = new TickDataPoint();

		tick.setBuy(getBuyOpen());
		tick.setDataSource(getDataSource());
		tick.setDate(getDate());
		tick.setInstrument(getInstrument());
		tick.setSell(getSellOpen());
		tick.setBuyVolume(getBuyVolume());
		tick.setSellVolume(getSellVolume());

		return tick;
	}

	public void setApproximatedValues(CandleDataPoint candle, boolean useCloseTime) {
		
		setPeriod(candle.getPeriod());
		setInstrument(candle.getInstrument());
		setDataSource(candle.getDataSource());

		if (useCloseTime) {
			buyOpen = candle.getBuyClose();
			buyClose = candle.getBuyClose();
			buyHigh = candle.getBuyClose();
			buyLow = candle.getBuyClose();

			sellOpen = candle.getSellClose();
			sellHigh = candle.getSellClose();
			sellLow = candle.getSellClose();
			sellClose = candle.getSellClose();
		} else {
			buyOpen = candle.getBuyOpen();
			buyClose = candle.getBuyOpen();
			buyHigh = candle.getBuyOpen();
			buyLow = candle.getBuyOpen();

			sellOpen = candle.getSellOpen();
			sellHigh = candle.getSellOpen();
			sellLow = candle.getSellOpen();
			sellClose = candle.getSellOpen();
		}

		setBuyVolume(0);
		setSellVolume(0);

		tickCount = 0;
	}

	public double getBuyOpen() {
		return buyOpen;
	}

	public void setBuyOpen(double buyOpen) {
		this.buyOpen = buyOpen;
	}

	public double getBuyHigh() {
		return buyHigh;
	}

	public void setBuyHigh(double buyHigh) {
		this.buyHigh = buyHigh;
	}

	public double getBuyLow() {
		return buyLow;
	}

	public void setBuyLow(double buyLow) {
		this.buyLow = buyLow;
	}

	public double getBuyClose() {
		return buyClose;
	}

	public void setBuyClose(double buyClose) {
		this.buyClose = buyClose;
	}

	public double getSellOpen() {
		return sellOpen;
	}

	public void setSellOpen(double sellOpen) {
		this.sellOpen = sellOpen;
	}

	public double getSellHigh() {
		return sellHigh;
	}

	public void setSellHigh(double sellHigh) {
		this.sellHigh = sellHigh;
	}

	public double getSellLow() {
		return sellLow;
	}

	public void setSellLow(double sellLow) {
		this.sellLow = sellLow;
	}

	public double getSellClose() {
		return sellClose;
	}

	public void setSellClose(double sellClose) {
		this.sellClose = sellClose;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public int getTickCount() {
		return tickCount;
	}

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}

	public double getHigh() {
		return getBuyHigh() + getSellHigh() / 2.0;
	}

	public double getLow() {
		return getBuyLow() + getSellLow() / 2.0;
	}

	public double getOpen() {
		return getBuyOpen() + getSellOpen() / 2.0;
	}

	public double getClose() {
		return getBuyClose() + getSellClose() / 2.0;
	}
}
