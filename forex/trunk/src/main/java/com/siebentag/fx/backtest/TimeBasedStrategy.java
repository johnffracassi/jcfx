package com.siebentag.fx.backtest;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.siebentag.fx.TickDataPoint;

public class TimeBasedStrategy extends AbstractStrategy
{
	private Order currentOrder;

	// parameters
	private LocalTime openTime;
	private LocalTime closeTime;
	private double stopLoss = 200.0;
	private OrderSide orderSide;

	// locals
	private String instrument;
	private LocalDateTime nextOpenAt;
	private LocalDateTime nextCloseAt;

	private DateTimeFormatter timeFormat = DateTimeFormat.forPattern("HHmm");
	
	public TimeBasedStrategy(BalanceSheet balanceSheet)
	{
		super(balanceSheet);
	}
	
	public String getName()
	{
		return timeFormat.print(openTime) + "-" + timeFormat.print(closeTime);
	}

	public String getSummaryFile()
	{
		return "tbs/" + instrument + "/" + orderSide + "/summary.html";
	}
	
	public String toString()
	{
		return openTime + "\t" + closeTime;
	}
	
	private void init(TickDataPoint tick)
	{
		this.instrument = tick.getInstrument();
		
		LocalDateTime firstDate = new LocalDateTime(tick.getDate().getTime());
		
		nextOpenAt = new LocalDateTime(firstDate);
		nextOpenAt = nextOpenAt.withTime(openTime.getHourOfDay(), openTime.getMinuteOfHour(), openTime.getSecondOfMinute(), openTime.getMillisOfSecond());
		
		if(nextOpenAt.isBefore(firstDate))
		{
			nextOpenAt = nextOpenAt.plusDays(1);
		}
		
		nextCloseAt = new LocalDateTime(firstDate);
		nextCloseAt = nextCloseAt.withTime(closeTime.getHourOfDay(), closeTime.getMinuteOfHour(), closeTime.getSecondOfMinute(), closeTime.getMillisOfSecond());

		while(nextCloseAt.isBefore(nextOpenAt))
		{
			nextCloseAt = nextCloseAt.plusDays(1);
		}
	}
	
	public void tick(TickDataPoint tick)
	{
		if(nextOpenAt == null && nextCloseAt == null)
		{
			init(tick);
		}

		LocalDateTime now = new LocalDateTime(tick.getDate());

		// if there is no open order, and the current time is after the openTime
		if(currentOrder == null && now.isAfter(nextOpenAt))
		{
			if(now.isBefore(nextOpenAt.plusMinutes(10)))
			{
				currentOrder = place(tick, orderSide, getLotSize());
			}
			else
			{
				nextCloseAt = nextTradeableTime(nextCloseAt);
			}
			
			nextOpenAt = nextTradeableTime(nextOpenAt);
		}
		// if there is an open order, and the current time is after closeTime
		else if(currentOrder != null && now.isAfter(nextCloseAt))
		{
			close(currentOrder, tick);
		}
		
		// check for stoploss
		if(currentOrder != null && currentOrder.getPipProfitLoss(tick) < -stopLoss)
		{
			close(currentOrder, tick);
		}
	}

	public void close(Order order, TickDataPoint tick)
	{
		super.close(order, tick);
		currentOrder = null;
		nextCloseAt = nextTradeableTime(nextCloseAt);
	}
	
	private LocalDateTime nextTradeableTime(LocalDateTime dateTime)
	{
		if(dateTime.getDayOfWeek() == DateTimeConstants.FRIDAY && dateTime.getHourOfDay() >= 22)
		{
			return dateTime.plusDays(2);
		}
		else if(dateTime.getDayOfWeek() == DateTimeConstants.SATURDAY && dateTime.getHourOfDay() < 20)
		{
			return dateTime.plusDays(2);
		}
		
		return dateTime.plusDays(1);
	}
	
	public Order getCurrentOrder()
	{
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder)
	{
		this.currentOrder = currentOrder;
	}

	public LocalTime getOpenTime()
	{
		return openTime;
	}

	public void setOpenTime(LocalTime openTime)
	{
		this.openTime = openTime;
	}

	public LocalTime getCloseTime()
	{
		return closeTime;
	}

	public void setCloseTime(LocalTime closeTime)
	{
		this.closeTime = closeTime;
	}

	public void registerIndicators()
	{
	}

	public OrderSide getOrderSide()
	{
		return orderSide;
	}

	public void setOrderSide(OrderSide orderSide)
	{
		this.orderSide = orderSide;
	}
}
