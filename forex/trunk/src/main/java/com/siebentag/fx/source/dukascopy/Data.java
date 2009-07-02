package com.siebentag.fx.source.dukascopy;

/**
 * @author Dukascopy (Suisse) SA, Dmitry Shohov
 */
public abstract class Data
{
	public long time;

	public Data()
	{
	}

	public Data(long time)
	{
		this.time = time;
	}

	public abstract void toBytes(byte[] buff, int off);

	public abstract int getBytesCount();

	protected static final int putLong(final byte[] b, final int off, final long val)
	{
		b[off + 7] = (byte) (val >>> 0);
		b[off + 6] = (byte) (val >>> 8);
		b[off + 5] = (byte) (val >>> 16);
		b[off + 4] = (byte) (val >>> 24);
		b[off + 3] = (byte) (val >>> 32);
		b[off + 2] = (byte) (val >>> 40);
		b[off + 1] = (byte) (val >>> 48);
		b[off + 0] = (byte) (val >>> 56);
		return off + 8;
	}

	protected static final int putDouble(final byte[] b, final int off, final double val)
	{
		final long j = Double.doubleToLongBits(val);
		b[off + 7] = (byte) (j >>> 0);
		b[off + 6] = (byte) (j >>> 8);
		b[off + 5] = (byte) (j >>> 16);
		b[off + 4] = (byte) (j >>> 24);
		b[off + 3] = (byte) (j >>> 32);
		b[off + 2] = (byte) (j >>> 40);
		b[off + 1] = (byte) (j >>> 48);
		b[off + 0] = (byte) (j >>> 56);
		return off + 8;
	}

	protected static final long getLong(final byte[] b, final int off)
	{
		return ((b[off + 7] & 0xFFL) << 0) + ((b[off + 6] & 0xFFL) << 8) + ((b[off + 5] & 0xFFL) << 16) + ((b[off + 4] & 0xFFL) << 24) + ((b[off + 3] & 0xFFL) << 32)
				+ ((b[off + 2] & 0xFFL) << 40) + ((b[off + 1] & 0xFFL) << 48) + (((long) b[off + 0]) << 56);
	}

	protected static final double getDouble(final byte[] b, final int off)
	{
		final long j = ((b[off + 7] & 0xFFL) << 0) + ((b[off + 6] & 0xFFL) << 8) + ((b[off + 5] & 0xFFL) << 16) + ((b[off + 4] & 0xFFL) << 24)
				+ ((b[off + 3] & 0xFFL) << 32) + ((b[off + 2] & 0xFFL) << 40) + ((b[off + 1] & 0xFFL) << 48) + (((long) b[off + 0]) << 56);
		return Double.longBitsToDouble(j);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		final Data other = (Data) obj;
		if (time != other.time)
			return false;
		
		return true;
	}
}
