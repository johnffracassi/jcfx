package com.jeff.fx.filter;

import com.jeff.fx.common.CandleDataPoint;

public class CandlePatternType
{
    /**
     * Candlesticks with a long upper shadow, long lower shadow and small real body are called spinning tops. One long
     * shadow represents a reversal of sorts; spinning tops represent indecision. The small real body (whether hollow
     * or filled) shows little movement from open to close, and the shadows indicate that both bulls and bears were
     * active during the session. Even though the session opened and closed with little change, prices moved
     * significantly higher and lower in the meantime. Neither buyers nor sellers could gain the upper hand and the
     * result was a standoff. After a long advance or long white candlestick, a spinning top indicates weakness among
     * the bulls and a potential change or interruption in trend. After a long decline or long black candlestick, a
     * spinning top indicates weakness among the bears and a potential change or interruption in trend
     */
    public static boolean isSpinningTop(CandleDataPoint candle)
    {
        int head = candle.getHeadSize();
        int body = Math.abs(candle.getSize());
        int tail = candle.getTailSize();
        int range = candle.getRange();

        if(range < 8)
            return false;

        double headPerc = ((double)head / range);
        double bodyPerc = ((double)body / range);
        double tailPerc = ((double)tail / range);

        return (headPerc > 0.38 && tailPerc > 0.38 && (bodyPerc <= 0.07 || body < 3));
    }

    /**
     * The Hammer is a bullish reversal pattern that forms after a decline. In addition to a potential trend reversal,
     * hammers can mark bottoms or support levels. After a decline, hammers signal a bullish revival.
     * The low of the long lower shadow implies that sellers drove prices lower during the session.
     * However, the strong finish indicates that buyers regained their footing to end the session on a strong note.
     * While this may seem enough to act on, hammers require further bullish confirmation. The low of the hammer shows
     * that plenty of sellers remain. Further buying pressure, and preferably on expanding volume, is needed before
     * acting. Such confirmation could come from a gap up or long white candlestick. Hammers are similar to selling
     * climaxes, and heavy volume can serve to reinforce the validity of the reversal
     */
    public static boolean isHammer(CandleDataPoint candle)
    {
        int head = candle.getHeadSize();
        int tail = candle.getTailSize();
        int range = candle.getRange();

        if(range < 10)
            return false;

        double headPerc = ((double)head / range);
        double tailPerc = ((double)tail / range);

        return (candle.getSize() > 0 && (headPerc < 0.20 || head < 4) && (tailPerc > 0.80 || tail >= 7));
    }

    /**
     * The Hanging Man is a bearish reversal pattern that can also mark a top or resistance level. Forming after an
     * advance, a Hanging Man signals that selling pressure is starting to increase. The low of the long lower shadow
     * confirms that sellers pushed prices lower during the session. Even though the bulls regained their footing and
     * drove prices higher by the finish, the appearance of selling pressure raises the yellow flag. As with the Hammer,
     * a Hanging Man requires bearish confirmation before action. Such confirmation can come as a gap down or long
     * black candlestick on heavy volume
     */
    public static boolean isShootingStar(CandleDataPoint candle)
    {
        int head = candle.getHeadSize();
        int tail = candle.getTailSize();
        int range = candle.getRange();

        if(range < 10)
            return false;

        double headPerc = ((double)head / range);
        double tailPerc = ((double)tail / range);

        return (candle.getSize() < 0 && (headPerc > 0.80 || head >= 7) && (tailPerc < 0.20 || tail < 4));
    }

    public static boolean isFullBull(CandleDataPoint candle)
    {
        if(candle.getRange() < 12)
            return false;

        return ((double)candle.getSize() / candle.getRange()) > 0.97;
    }

    public static boolean isFullBear(CandleDataPoint candle)
    {
        if(candle.getRange() < 12)
            return false;

        return ((double)candle.getSize() / candle.getRange()) < -0.97;
    }

    public static boolean isDoji(CandleDataPoint candle)
    {
        int body = Math.abs(candle.getSize());
        int range = candle.getRange();

        if(range < 10)
            return false;

        return body < 2;
    }
}

