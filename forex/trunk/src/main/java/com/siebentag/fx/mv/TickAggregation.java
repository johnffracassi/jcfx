package com.siebentag.fx.mv;

import java.util.Date;

public class TickAggregation implements Comparable<TickAggregation> 
{
	private Date date;
	private OHLC buy;
	private OHLC sell;

	public OHLC getBuy() {
		return buy;
	}

	public void setBuy(OHLC buy) {
		this.buy = buy;
	}

	public OHLC getSell() {
		return sell;
	}

	public void setSell(OHLC sell) {
		this.sell = sell;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int compareTo(TickAggregation row) {
		return date.compareTo(row.date);
	}
}
