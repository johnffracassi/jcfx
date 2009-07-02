package com.siebentag.fx.backtest;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.source.Instrument;

public abstract class AbstractStrategy implements Strategy
{
	private BalanceSheet balanceSheet;
	
	// parameters
	private Instrument instrument;
	private double lotSize;
	
	public AbstractStrategy(BalanceSheet balanceSheet)
	{
		this.balanceSheet = balanceSheet;
	}

	public void output()
	{
		System.out.println(toString() + "\t" + balanceSheet);
	}
	

	public BalanceSheet getBalanceSheet()
	{
		return balanceSheet;
	}
	
	public Order place(TickDataPoint tick, OrderSide side, double lots)
	{
//		System.out.printf("%s OpenTrade [%s], %s, %s @ %.4f%n", Thread.currentThread().getName(), toString(), tick.getDate(), side, (side==OrderSide.Buy?tick.getBuy():tick.getSell()));
//		System.out.flush();

		Order order = (side == OrderSide.Buy) ? new BuyOrder() : new SellOrder();
		order.setLots(lots);
		order.setOpen(tick);
		balanceSheet.postOrder(order);
		
		return order;
	}
	
	public void close(Order order, TickDataPoint tick)
	{
		order.setClose(tick, OrderCloseType.Close);
		balanceSheet.closeOrder(order);

//		System.out.printf("%s ClosedTrade [%s], %s, PipPL=%.2f%n", Thread.currentThread().getName(), toString(), tick.getDate(), order.getProfitLoss());
//		System.out.flush();
	}

	public boolean acceptsInstrument(Instrument i)
	{
		return (instrument.equals(i));
	}

	public Instrument getInstrument()
	{
		return instrument;
	}

	public void setInstrument(Instrument instrument)
	{
		this.instrument = instrument;
	}

	public double getLotSize()
	{
		return lotSize;
	}

	public void setLotSize(double lotSize)
	{
		this.lotSize = lotSize;
	}
}
