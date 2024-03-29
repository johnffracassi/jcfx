package com.siebentag.fx;

public class CandleStickDataPoint extends DataPoint
{
	private String period;
	
	private double buyOpen;
	private double buyHigh;
	private double buyLow;
	private double buyClose;
	
	private double sellOpen;
	private double sellHigh;
	private double sellLow;
	private double sellClose;
	
	private int tickCount;

	
	public CandleStickDataPoint()
	{
	}

	public CandleStickDataPoint(CandleStickDataPoint candle)
	{
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
	
	@Override
	public String toString()
	{
		return String.format("[%s/%s/%s] %s / %.4f,%.4f,%.4f,%.4f", getInstrument(), getDataSource(), period, getDate(), buyOpen, buyHigh, buyLow, buyClose);
	}
	
	public void merge(CandleStickDataPoint newClose)
	{
		buyClose = newClose.buyClose;
		sellClose = newClose.sellClose;
		
		if(newClose.buyHigh > buyHigh) buyHigh = newClose.buyHigh;
		if(newClose.sellHigh > sellHigh) sellHigh = newClose.sellHigh;
		
		if(newClose.buyLow < buyLow) buyLow = newClose.buyLow;
		if(newClose.sellLow < sellLow) sellLow = newClose.sellLow;
		
		tickCount += newClose.tickCount;
	}
	
	/**
	 * Convert the candle to a tick (based on opening price)
	 * 
	 * @return
	 */
	public TickDataPoint toTick()
	{
		TickDataPoint tick = new TickDataPoint();
		
		tick.setBuy(getBuyOpen());
		tick.setDataSource(getDataSource());
		tick.setDate(getDate());
		tick.setInstrument(getInstrument());
		tick.setJobId(getJobId());
		tick.setSell(getSellOpen());
		tick.setBuyVolume(getBuyVolume());
		tick.setSellVolume(getSellVolume());
		
		return tick;
	}
	
	public void setApproximatedValues(CandleStickDataPoint candle)
	{
		setPeriod(candle.getPeriod());
		setInstrument(candle.getInstrument());
		setDataSource(candle.getDataSource());
		
		buyOpen = candle.getBuyClose();
		buyClose = candle.getBuyClose();
		buyHigh = candle.getBuyClose();
		buyLow = candle.getBuyClose();
		
		sellOpen = candle.getSellClose();
		sellHigh = candle.getSellClose();
		sellLow = candle.getSellClose();
		sellClose = candle.getSellClose();
		
		setBuyVolume(0);
		setSellVolume(0);

		tickCount = 0;
	}
	
	public double getBuyOpen()
	{
		return buyOpen;
	}

	public void setBuyOpen(double buyOpen)
	{
		this.buyOpen = buyOpen;
	}

	public double getBuyHigh()
	{
		return buyHigh;
	}

	public void setBuyHigh(double buyHigh)
	{
		this.buyHigh = buyHigh;
	}

	public double getBuyLow()
	{
		return buyLow;
	}

	public void setBuyLow(double buyLow)
	{
		this.buyLow = buyLow;
	}

	public double getBuyClose()
	{
		return buyClose;
	}

	public void setBuyClose(double buyClose)
	{
		this.buyClose = buyClose;
	}

	public double getSellOpen()
	{
		return sellOpen;
	}

	public void setSellOpen(double sellOpen)
	{
		this.sellOpen = sellOpen;
	}

	public double getSellHigh()
	{
		return sellHigh;
	}

	public void setSellHigh(double sellHigh)
	{
		this.sellHigh = sellHigh;
	}

	public double getSellLow()
	{
		return sellLow;
	}

	public void setSellLow(double sellLow)
	{
		this.sellLow = sellLow;
	}

	public double getSellClose()
	{
		return sellClose;
	}

	public void setSellClose(double sellClose)
	{
		this.sellClose = sellClose;
	}

	public String getPeriod()
    {
    	return period;
    }

	public void setPeriod(String period)
    {
    	this.period = period;
    }

	public int getTickCount()
	{
		return tickCount;
	}

	public void setTickCount(int tickCount)
	{
		this.tickCount = tickCount;
	}
}
