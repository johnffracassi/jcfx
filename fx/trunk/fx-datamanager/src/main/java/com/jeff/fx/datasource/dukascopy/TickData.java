package com.jeff.fx.datasource.dukascopy;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author Dukascopy (Suisse) SA, Dmitry Shohov
 */
public class TickData extends Data
{
	// number of bytes in binary representation
	public static final int BYTES_COUNT = 8 + 8 * 4; // time + ask + bid + askVol + bidVol

	public double ask;
	public double bid;
	public double askVol;
	public double bidVol;

	public TickData()
	{
	}

	public TickData(long time, double ask, double bid, double askVol, double bidVol)
	{
		super(time);
		this.ask = ask;
		this.bid = bid;
		this.askVol = askVol;
		this.bidVol = bidVol;
	}

	public void fromBytes(byte[] bytes, int off)
	{
		time = getLong(bytes, off);
		ask = getDouble(bytes, off + 1 * 8);
		bid = getDouble(bytes, off + 2 * 8);
		askVol = getDouble(bytes, off + 3 * 8);
		bidVol = getDouble(bytes, off + 4 * 8);
	}

	public final void toBytes(byte[] buff, int off)
	{
		off = putLong(buff, off, time);
		off = putDouble(buff, off, ask);
		off = putDouble(buff, off, bid);
		off = putDouble(buff, off, askVol);
		putDouble(buff, off, bidVol);
	}

	public int getBytesCount()
	{
		return BYTES_COUNT;
	}

	public String toString()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		StringBuilder stamp = new StringBuilder();
		stamp.append(time).append("[").append(format.format(time)).append("] / ");
		stamp.append(ask).append(" / ").append(bid);
		return stamp.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		
		temp = Double.doubleToLongBits(ask);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		temp = Double.doubleToLongBits(askVol);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		temp = Double.doubleToLongBits(bid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		temp = Double.doubleToLongBits(bidVol);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		
		if (!super.equals(obj))
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		TickData other = (TickData) obj;
		if (Double.doubleToLongBits(ask) != Double.doubleToLongBits(other.ask))
			return false;
		
		if (Double.doubleToLongBits(askVol) != Double.doubleToLongBits(other.askVol))
			return false;
		
		if (Double.doubleToLongBits(bid) != Double.doubleToLongBits(other.bid))
			return false;
		
		if (Double.doubleToLongBits(bidVol) != Double.doubleToLongBits(other.bidVol))
			return false;
		
		return true;
	}
}
