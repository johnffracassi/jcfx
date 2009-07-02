package com.siebentag.fx.tbv;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.backtest.BalanceSheet;
import com.siebentag.fx.backtest.BuyOrder;
import com.siebentag.fx.backtest.Order;
import com.siebentag.fx.backtest.OrderCloseType;
import com.siebentag.fx.backtest.OrderSide;
import com.siebentag.fx.backtest.SellOrder;

public class OrderGenerator
{
	private static final LocalTime MIDNIGHT = new LocalTime(0, 0, 0);

	public static BalanceSheet createBalanceSheet(List<CandleStickDataPoint> candles, OrderSide orderSide, double stop, LocalDate startDate, LocalTime openTime, LocalTime closeTime)
	{
		BalanceSheet orderBook = new BalanceSheet();
		Order order = null;

		// if there aren't any candles, don't process
		if(candles.size() == 0)
		{
			return orderBook;
		}
		
		// make sure there is a startDate
		if(startDate == null)
		{
			startDate = candles.get(0).getLocalDateTime().toLocalDate();
		}
		
		for(CandleStickDataPoint candle : candles)
		{
			if(candle.getLocalDateTime().isAfter(startDate.toLocalDateTime(MIDNIGHT)))
			{
				TickDataPoint tick = candle.toTick();
				
				if(order == null)
				{
					// check for open time
					if(candle.getLocalDateTime().toLocalTime().equals(openTime))
					{
						order = (orderSide == OrderSide.Buy) ? new BuyOrder() : new SellOrder();
						order.setOpen(tick);
						order.setStop(stop);
						order.setLots(0.5);
						orderBook.postOrder(order);
					}
				}
				else
				{
					// check for stop-loss
					if(order.stopReached(tick))
					{
						order.setClose(tick, OrderCloseType.StopLoss);
					}
					else if(order.limitReached(tick))
					{
						order.setClose(tick, OrderCloseType.Limit);
					}
					else if(candle.getLocalDateTime().toLocalTime().equals(closeTime))
					{
						order.setClose(tick, OrderCloseType.Close);
					}

					if(order.isClosed())
					{
						orderBook.closeOrder(order);
						order = null;
					}
				}
			}
		}
		
		return orderBook;
	}
}
