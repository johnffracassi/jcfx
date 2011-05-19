package com.jeff.fx.filter.candletype;

import com.jeff.fx.common.CandleDataPoint;

public abstract class BasicCandleTypeDef implements CandleTypeDef
{
    protected double minRange = 0.0;
    protected double maxRange = Double.POSITIVE_INFINITY;

    protected double minSize = Double.NEGATIVE_INFINITY;
    protected double maxSize = Double.POSITIVE_INFINITY;

    // high - max(open,close)
    protected double minHeadSize = 0.0;
    protected double maxHeadSize = 1.0;

    // max(open,close) - min(open,close)
    protected double minBodySize = 0.0;
    protected double maxBodySize = 1.0;

    // min(open,close) - low
    protected double minTailSize = 0.0;
    protected double maxTailSize = 1.0;

    public boolean is(CandleDataPoint candle)
    {
        return (verifySize(candle) && verifyRange(candle) && verifyHeadSize(candle) && verifyBodySize(candle) && verifyTailSize(candle));
    }

    private boolean verifyRange(CandleDataPoint candle)
    {
        return (candle.getRange() >= minRange && candle.getRange() <= maxRange);
    }

    private boolean verifySize(CandleDataPoint candle)
    {
        return (candle.getSize() >= minSize && candle.getSize() <= maxSize);
    }

    private boolean verifyHeadSize(CandleDataPoint candle)
    {
        double perc = 0.0;
        if(candle.getRange() > 0)
            perc = (double)candle.getHeadSize() / (double)candle.getRange();

        return (perc >= minHeadSize && perc <= maxHeadSize);
    }

    private boolean verifyBodySize(CandleDataPoint candle)
    {
        double perc = 0.0;
        if(candle.getRange() > 0)
            perc = (double)Math.abs(candle.getSize()) / (double)candle.getRange();

        return (perc >= minBodySize && perc <= maxBodySize);
    }

    private boolean verifyTailSize(CandleDataPoint candle)
    {
        double perc = 0.0;
        if(candle.getRange() > 0)
            perc = (double)Math.abs(candle.getTailSize()) / (double)candle.getRange();

        return (perc >= minTailSize && perc <= maxTailSize);
    }
}

