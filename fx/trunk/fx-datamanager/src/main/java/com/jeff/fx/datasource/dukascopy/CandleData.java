package com.jeff.fx.datasource.dukascopy;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author Dukascopy (Suisse) SA, Dmitry Shohov
 */
public class CandleData extends Data
{
	// number of bytes in binary representation
	public static final int BYTES_COUNT = 8 * 6; // 5 bids + 1 time

	public double open;
	public double close;
	public double low;
	public double high;
	public double vol;

	public CandleData()
	{
	}

	public CandleData(long time, double open, double close, double low, double high, double vol)
	{
		super(time);
		this.open = open;
		this.close = close;
		this.low = low;
		this.high = high;
		this.vol = vol;
	}

	public int getBytesCount()
	{
		return BYTES_COUNT;
	}

	public void fromBytes(byte[] bytes, int off)
	{
		time = getLong(bytes, off);
		open = getDouble(bytes, off + 1 * 8);
		close = getDouble(bytes, off + 2 * 8);
		low = getDouble(bytes, off + 3 * 8);
		high = getDouble(bytes, off + 4 * 8);
		vol = getDouble(bytes, off + 5 * 8);
	}

	public void toBytes(byte[] buff, int off)
	{
		if (buff.length < off + BYTES_COUNT)
		{
			throw new ArrayIndexOutOfBoundsException("Buffer too short");
		}
		off = putLong(buff, off, time);
		off = putDouble(buff, off, open);
		off = putDouble(buff, off, close);
		off = putDouble(buff, off, low);
		off = putDouble(buff, off, high);
		putDouble(buff, off, vol);
	}

	public String toString()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		StringBuilder stamp = new StringBuilder();
		stamp.append(time).append("[").append(format.format(time)).append("] / ");
		stamp.append(open).append(" - ").append(close);
		return stamp.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(close);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(high);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(low);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(open);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vol);
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
		
		final CandleData other = (CandleData) obj;
		if (Double.doubleToLongBits(close) != Double.doubleToLongBits(other.close))
			return false;
		
		if (Double.doubleToLongBits(high) != Double.doubleToLongBits(other.high))
			return false;
		
		if (Double.doubleToLongBits(low) != Double.doubleToLongBits(other.low))
			return false;
		
		if (Double.doubleToLongBits(open) != Double.doubleToLongBits(other.open))
			return false;
		
		if (Double.doubleToLongBits(vol) != Double.doubleToLongBits(other.vol))
			return false;
		
		return true;
	}
}
