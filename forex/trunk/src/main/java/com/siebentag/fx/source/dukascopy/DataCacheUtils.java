package com.siebentag.fx.source.dukascopy;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

import com.siebentag.fx.source.Instrument;
import com.siebentag.fx.source.OfferSide;
import com.siebentag.fx.source.Period;

/**
* @author Dukascopy (Suisse) SA, Dmitry Shohov
 */
public class DataCacheUtils 
{
    public static final long getChunkStart(final Period period, final long time) 
    {
        if (period == Period.TICK) 
        {
            return getClosestHourStartBefore(time);
        } 
        else 
        {
            switch (period.unit) 
            {
                case Second:
                case Minute:
                    return getClosestDayStartBefore(time);
               
                case Hour:
                case Day:
                    return getClosestMonthStartBefore(time);
                
                case Week:
                case Month:
                    return getClosestYearStartBefore(time);
                
                default:
                    throw new RuntimeException("Period not supported");
            }
        }
    }

    public static final long getChunkEnd(final Period period, final long time) 
    {
        final long chunkStart = getChunkStart(period, time);
    
        if (period == Period.TICK) 
        {
            return chunkStart + 60 * 60 * 1000;
        } 
        else 
        {
            switch (period.unit) 
            {
                case Second:
                case Minute:
                    return chunkStart + 24 * 60 * 60 * 1000 - period.getInterval();
                
                case Hour:
                case Day:
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.add(Calendar.MONTH, 1);
                    return cal.getTimeInMillis() - period.getInterval();
                
                case Week:
                    cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.add(Calendar.YEAR, 1);
                    long yearEnd = cal.getTimeInMillis();
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    if (yearEnd <= cal.getTimeInMillis()) {
                        cal.add(Calendar.WEEK_OF_YEAR, -1);
                    }
                    return cal.getTimeInMillis();
                
                case Month:
                    cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.add(Calendar.YEAR, 1);
                    yearEnd = cal.getTimeInMillis();
                    cal.add(Calendar.MONTH, -1);
                    return cal.getTimeInMillis();
                
                default:
                    throw new RuntimeException("Period not supported");
            }
        }
    }

    public static final long getClosestHourStartBefore(final long from) 
    {
        return from - from % (60 * 60 * 1000);
    }

    public static final long getClosestDayStartBefore(final long from) 
    {
        return from - from % (24 * 60 * 60 * 1000);
    }

    public static final long getClosestMonthStartBefore(final long from) 
    {
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(from);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTimeInMillis();
    }
    
    public static final long getClosestYearStartBefore(final long from)
    {
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(from);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        return cal.getTimeInMillis();
    }

    public static final long getCandleStart(final Period period, final long time) 
    {
        final long intervalInMs = period.getInterval();
        switch (period.unit) 
        {
            case Second:
            case Minute:
                final long dayStart = getClosestDayStartBefore(time);
                return dayStart + ((time - dayStart) / intervalInMs) * intervalInMs;
            
            case Hour:
            case Day:
                final long monthStart = getClosestMonthStartBefore(time);
                return monthStart + ((time - monthStart) / intervalInMs) * intervalInMs;
            
            case Week:
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                cal.setTimeInMillis(time);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                if (cal.getTimeInMillis() > time) {
                    cal.add(Calendar.WEEK_OF_YEAR, -1);
                }
                return cal.getTimeInMillis();
            
            case Month:
                cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                cal.setTimeInMillis(time);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                return cal.getTimeInMillis();
            
            default:
                return Long.MIN_VALUE;
        }
    }
    
    public static final String getChunkFileName(final Instrument instrument, final Period period, final OfferSide side, final long from) 
    {
        final StringBuilder fileName = new StringBuilder();
        fileName.append(instrument.name()).append(File.separatorChar);

        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(from);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        fileName.append(cal.get(Calendar.YEAR)).append(File.separatorChar);
        
        if (period == Period.TICK) {
            if (month < 10) {
                fileName.append("0");
            }
            fileName.append(month).append(File.separatorChar);
            if (day < 10) {
                fileName.append("0");
            }
            fileName.append(day).append(File.separatorChar);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if (hour < 10) {
                fileName.append("0");
            }
            fileName.append(hour).append("h").append("_").append("ticks");
        } else {
            switch (period.unit) {
                case Month:
                    fileName.append(side.name()).append("_").append("candles").append("_").append("month").append("_").append(period.numOfUnits);
                    break;
                case Week:
                    fileName.append(side.name()).append("_").append("candles").append("_").append("week").append("_").append(period.numOfUnits);
                    break;
                case Day:
                    if (month < 10) {
                        fileName.append("0");
                    }
                    fileName.append(month).append(File.separatorChar);
                    fileName.append(side.name()).append("_").append("candles").append("_").append("day").append("_").append(period.numOfUnits);
                    break;
                case Hour:
                    if (month < 10) {
                        fileName.append("0");
                    }
                    fileName.append(month).append(File.separatorChar);
                    fileName.append(side.name()).append("_").append("candles").append("_").append("hour").append("_").append(period.numOfUnits);
                    break;
                case Minute:
                    if (month < 10) {
                        fileName.append("0");
                    }
                    fileName.append(month).append(File.separatorChar);
                    if (day < 10) {
                        fileName.append("0");
                    }
                    fileName.append(day).append(File.separatorChar);
                    fileName.append(side.name()).append("_").append("candles").append("_").append("min").append("_").append(period.numOfUnits);
                    break;
                case Second:
                    if (month < 10) {
                        fileName.append("0");
                    }
                    fileName.append(month).append(File.separatorChar);
                    if (day < 10) {
                        fileName.append("0");
                    }
                    fileName.append(day).append(File.separatorChar);
                    fileName.append(side.name()).append("_").append("candles").append("_").append("sec").append("_").append(period.numOfUnits);
                    break;
                default:
                    throw new RuntimeException("Periods not supported");
            }
        }
        fileName.append(".bin");
        return fileName.toString();
    }
    

    public static final long getNextChunkStart(final Period period, final long time) {
        final long chunkStart = getChunkStart(period, time);
        if (period == Period.TICK) {
            return chunkStart + 60 * 60 * 1000;
        } else {
            switch (period.unit) {
                case Second:
                case Minute:
                    return chunkStart + 24 * 60 * 60 * 1000;
                case Hour:
                case Day:
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.add(Calendar.MONTH, 1);
                    return cal.getTimeInMillis();
                case Week:
                case Month:
                    cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.add(Calendar.YEAR, 1);
                    return cal.getTimeInMillis();
                default:
                    throw new RuntimeException("Period not supported");
            }
        }
    }

    public static final long getPreviousChunkStart(final Period period, final long time) {
        final long chunkStart = getChunkStart(period, time);
        if (period == Period.TICK) {
            return chunkStart - 60 * 60 * 1000;
        } else {
            switch (period.unit) {
                case Second:
                case Minute:
                    return chunkStart - 24 * 60 * 60 * 1000;
                case Hour:
                case Day:
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.add(Calendar.MONTH, -1);
                    return cal.getTimeInMillis();
                case Week:
                case Month:
                    cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.add(Calendar.YEAR, -1);
                    return cal.getTimeInMillis();
                default:
                    throw new RuntimeException("Period not supported");
            }
        }
    }

    public static final int getCandleCountInChunk(final Period period, final long time) {
        final long chunkStart = getChunkStart(period, time);
        final long chunkEnd = getChunkEnd(period, time);
        if (period == Period.TICK) {
            throw new RuntimeException("Not a candle");
        } else {
            switch (period.unit) {
                case Second:
                case Minute:
                case Hour:
                case Day:
                    return (int) ((chunkEnd - chunkStart) / period.getInterval() + 1);
                case Week:
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    cal.setTimeInMillis(chunkStart);
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    if (cal.getTimeInMillis() < chunkStart) {
                        cal.add(Calendar.WEEK_OF_YEAR, 1);
                    }
                    int count = 0;
                    do {
                        cal.add(Calendar.WEEK_OF_YEAR, 1);
                        ++count;
                    } while (cal.getTimeInMillis() <= chunkEnd);
                    return count;
                case Month:
                    return 12;
                default:
                    throw new RuntimeException("Period not supported");
            }
        }
    }

    public static final long getNextCandleStart(final Period period, final long candleStart) {
        switch (period.unit) {
            case Second:
            case Minute:
            case Hour:
            case Day:
            case Week:
                return candleStart + period.getInterval();
            case Month:
                final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                cal.setTimeInMillis(candleStart);
                cal.add(Calendar.MONTH, 1);
                return cal.getTimeInMillis();
            default:
                return Long.MIN_VALUE;
        }
    }

    public static final long getPreviousCandleStart(final Period period, final long candleStart) {
        switch (period.unit) {
            case Second:
            case Minute:
            case Hour:
            case Day:
            case Week:
                return candleStart - period.getInterval();
            case Month:
                final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                cal.setTimeInMillis(candleStart);
                cal.add(Calendar.MONTH, -1);
                return cal.getTimeInMillis();
            default:
                return Long.MIN_VALUE;
        }
    }

    public static final int getCandlesCountBetween(final Period period, final long fromCandleStart, final long toCandleStartIncluding) {
        switch (period.unit) {
            case Second:
            case Minute:
            case Hour:
            case Day:
            case Week:
                return (int) ((toCandleStartIncluding - fromCandleStart) / period.getInterval()) + 1;
            case Month:
                int count = 0;
                final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                cal.setTimeInMillis(fromCandleStart);
                while (cal.getTimeInMillis() <= toCandleStartIncluding) {
                    ++count;
                    cal.add(Calendar.MONTH, 1);
                }
                return count;
            default:
                return Integer.MIN_VALUE;
        }
    }
}