package com.jeff.fx.backtest.strategy.time;

import com.jeff.fx.backtest.engine.AbstractStrategy;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.engine.OrderCloseType;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.backtest.strategy.coder.Description;
import com.jeff.fx.backtest.strategy.coder.Optimiser;
import com.jeff.fx.backtest.strategy.coder.Parameter;
import com.jeff.fx.common.*;
import com.jeff.fx.indicator.overlay.SimpleMovingAverage;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

import java.util.Map;

public class TimeStrategy extends AbstractStrategy {
	
	private static Logger log = Logger.getLogger(TimeStrategy.class);
	
	@Parameter("Open Time")
	@Description("Open a trade at this time")
    @Optimiser(min=1320, max=8460, step=60, start=1320, end=1320+2880)
	private TimeOfWeek open = null;
	
	@Parameter("Close Time")
	@Description("Close the open trade at this time")
    @Optimiser(min=1320, max=8460, step=60, start=1320+2880, end=8460)
	private TimeOfWeek close = null;

	@Parameter("Stop Loss")
	@Description("Close order if it loses this amount")
	@Optimiser(min=0, max=500, step=100, start=0, end=300)
    private int stopLoss = 0;

	@Parameter("Take Profit")
	@Description("Close order if it reaches this amount of profit")
    @Optimiser(min=0, max=500, step=100, start=0, end=300)
	private int takeProfit = 0;

	@Parameter("Short SMA")
	@Description("Only open the trade if SMA(x) is heading in direction of the offer side")
    @Optimiser(min=3, max=50, step=7, start=5, end=47)
	private int shortSma = 14;
	
	@Parameter("Long SMA")
	@Description("Only open the trade if SMA(y) is heading in direction of the offer side")
    @Optimiser(min=50, max=500, step=66, start=50, end=350)
	private int longSma = 140;
	
	@Parameter("Offer Side")
	@Description("Type of order to open")
	private OfferSide offerSide = OfferSide.Buy;

	public TimeStrategy(int id, Map<String,Object> parameters, IndicatorCache indicators) {
		
		super(id);
		
		this.indicators = indicators;
		
		shortSma = (Integer)parameters.get("shortSma");
		longSma = (Integer)parameters.get("longSma");
		stopLoss = (Integer)parameters.get("stopLoss");
		takeProfit = (Integer)parameters.get("takeProfit");
		open = (TimeOfWeek)parameters.get("open");
		close = (TimeOfWeek)parameters.get("close");
		offerSide = (OfferSide)parameters.get("offerSide");
	}
	
	public boolean isTestValid() {
		return (!open.equals(close));
	}

	public OrderBook execute(CandleCollection candles) {

        SimpleMovingAverage sma1 = (SimpleMovingAverage)indicators.calculate(new SimpleMovingAverage(shortSma, CandleValueModel.Typical), candles);
        SimpleMovingAverage sma2 = (SimpleMovingAverage)indicators.calculate(new SimpleMovingAverage(longSma, CandleValueModel.Typical), candles);

        if(candles != null && candles.getStart() != null) {

            LocalDateTime date = candles.getStart();

            int weekIdx = 0;
            while(date.isBefore(candles.getEnd())) {

                CandleWeek cw = candles.getCandleWeek(date);
				
				if(close.isAfter(open)) {
				
					CandleDataPoint openCandle = cw.getCandle(open);
					CandleDataPoint closeCandle = cw.getCandle(close);
					
					if(openCandle != null && closeCandle != null) {
						
						int absOpenIdx = weekIdx * cw.getCandleCount() + cw.getCandleIndex(open);
                        boolean buying = (offerSide == OfferSide.Buy);
                        boolean selling = (offerSide == OfferSide.Sell);
                        boolean shortAboveLong = (sma1.getValue(absOpenIdx) - sma2.getValue(absOpenIdx)) > 0;
                        int dir1 = sma1.getDirection(absOpenIdx);
                        int dir2 = sma2.getDirection(absOpenIdx);
                        boolean bothUp = (dir1 == 1 && dir2 == 1);
                        boolean bothDown = (dir1 == -1 && dir2 == -1);
                        boolean openAboveShort = openCandle.getOpen() > sma1.getValue(absOpenIdx);

						if((buying && bothUp && shortAboveLong && openAboveShort) || (selling && bothDown && !shortAboveLong && !openAboveShort))
						{
							// create and lodge the order
							BTOrder order = new BTOrder();
							order.setOfferSide(offerSide);
							order.setUnits(1.0);
							if(takeProfit > 0) order.setTakeProfit(takeProfit);
							if(stopLoss > 0) order.setStopLoss(stopLoss);
							openOrder(order, openCandle);
			
							// search for a SL/TP
							CandleDataPoint sl = null;
							CandleDataPoint tp = null;
			
							// find a stop loss
							if(stopLoss > 0 && buying) { // find a SL on buy
								sl = cw.findNextLowBelowPrice(open, close, (float)order.getStopLossPrice(), offerSide);
							} else if(stopLoss > 0 && selling) { // find a SL on sell
								sl = cw.findNextHighAbovePrice(open, close, (float)order.getStopLossPrice(), offerSide);
							}
							
							// find a take profit
							if(takeProfit > 0 && buying) { // find a TP on buy
								tp = cw.findNextHighAbovePrice(open, close, (float)order.getTakeProfitPrice(), offerSide);
							} else if(takeProfit > 0 && selling) { // find a TP on sell
								tp = cw.findNextLowBelowPrice(open, close, (float)order.getTakeProfitPrice(), offerSide);
							}
			
							// find which comes first (sl, tp or close)
							if(sl == null && tp == null) {
								order.setClosePrice(buying ? closeCandle.getSellClose() : closeCandle.getBuyClose());
								order.setCloseType(OrderCloseType.Close);
								order.setCloseTime(closeCandle.getDateTime());
							} else if(sl != null && tp != null) {
								if(sl.getDateTime().isBefore(tp.getDateTime())) {
									order.setClosePrice(order.getStopLossPrice());
									order.setCloseType(OrderCloseType.StopLoss);
									order.setCloseTime(sl.getDateTime());
								} else {
									order.setClosePrice(order.getTakeProfitPrice());
									order.setCloseType(OrderCloseType.TakeProfit);
									order.setCloseTime(tp.getDateTime());
								}
							} else if(sl != null) {
								order.setClosePrice(order.getStopLossPrice());
								order.setCloseType(OrderCloseType.StopLoss);
								order.setCloseTime(sl.getDateTime());
							} else if(tp != null) {
								order.setClosePrice(order.getTakeProfitPrice());
								order.setCloseType(OrderCloseType.TakeProfit);
								order.setCloseTime(tp.getDateTime());
							}
							
							getOrderBook().close(order);	
						}
					}
				}
				
				weekIdx ++;
				date = date.plusDays(7);
			}
		}
		
		return getOrderBook();
	}

	public void setOfferSide(OfferSide offerSide) {
		this.offerSide = offerSide;
	}
	
	public OfferSide getOfferSide() {
		return offerSide == null ? OfferSide.Buy : offerSide;
	}
	
	public int getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(int stopLoss) {
		this.stopLoss = stopLoss;
	}

	public int getTakeProfit() {
		return takeProfit;
	}

	public void setTakeProfit(int takeProfit) {
		this.takeProfit = takeProfit;
	}

	public TimeOfWeek getOpen() {
		return open;
	}

	public void setOpen(TimeOfWeek open) {
		this.open = open;
	}

	public TimeOfWeek getClose() {
		return close;
	}

	public void setClose(TimeOfWeek close) {
		this.close = close;
	}

	public int getShortSma() {
		return shortSma;
	}

	public void setShortSma(int shortSma) {
		this.shortSma = shortSma;
	}

	public int getLongSma() {
		return longSma;
	}

	public void setLongSma(int longSma) {
		this.longSma = longSma;
	}
}
