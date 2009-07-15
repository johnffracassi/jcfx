package com.jeff.fx.datastore.converter;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;

public class TickCollection extends ArrayList<TickDataPoint>
{
    private static final long serialVersionUID = -3815367748997163466L;

    private PriceList buys = new PriceList("buy");
	private PriceList sells = new PriceList("sell");
	private long buyVol = 0;
	private long sellVol = 0;
	private Instrument instrument = null;
	private FXDataSource dataSource = null;
	private LocalDateTime openDateTime;

	public TickCollection(LocalDateTime date)
	{
		super();
		this.openDateTime = date;
	}
	
	public TickCollection(LocalDateTime date, List<TickDataPoint> ticks)
	{
		super(ticks.size());
		
		this.openDateTime = date;
		
		for(TickDataPoint tick : ticks)
		{
			add(tick);
		}
	}
	
	public void reset()
	{
		clear();

		buys = new PriceList("buy");
		sells = new PriceList("sell");
		buyVol = 0;
		sellVol = 0;
	}
	
	public CandleDataPoint toCandle(Period period)
	{
		CandleDataPoint cs = new CandleDataPoint();
		
		cs.setDate(openDateTime);
		cs.setDataSource(dataSource);
		cs.setPeriod(period);
		cs.setInstrument(instrument);

		if(size() > 0)
		{
			cs.setBuyOpen(getOpen().getBuy());
			cs.setBuyClose(getClose().getBuy());
			cs.setBuyHigh(buys.max());
			cs.setBuyLow(buys.min());
			
			cs.setSellOpen(getOpen().getSell());
			cs.setSellClose(getClose().getSell());
			cs.setSellHigh(sells.max());
			cs.setSellLow(sells.min());
			
			cs.setBuyVolume(buyVol);
			cs.setSellVolume(sellVol);
		}
		
		cs.setTickCount(size());
		
		return cs;
	}
	
	@Override public void add(int index, TickDataPoint element)
	{
	    super.add(index, element);
	    addPriceData(element);
	}
	
	@Override public boolean add(TickDataPoint element)
	{
	    super.add(element);
	    addPriceData(element);
	    return true;
	}

	private void addPriceData(TickDataPoint element)
	{
		if(dataSource == null)
		{
			dataSource = element.getDataSource();
		}
		
		if(instrument == null)
		{
			instrument = element.getInstrument();
		}
		
	    buys.addPrice(String.valueOf(element.getDate()), element.getBuy());
	    sells.addPrice(String.valueOf(element.getDate()), element.getSell());
	    buyVol += element.getBuyVolume();
	    sellVol += element.getSellVolume();
	}
	
	public TickDataPoint getOpen()
	{
		if(size() > 0)
		{
			return get(0);
		}
		else
		{
			return null;
		}
	}
	
	public TickDataPoint getClose()
	{
		if(size() > 0)
		{
			return get(size() - 1);
		}
		else
		{
			return null;
		}
	}
	
	public PriceList getBuyList()
	{
		return buys;
	}
	
	public PriceList getSellList()
	{
		return sells;
	}
}