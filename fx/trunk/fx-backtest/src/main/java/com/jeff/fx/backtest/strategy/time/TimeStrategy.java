package com.jeff.fx.backtest.strategy.time;

import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.jeff.fx.backtest.engine.AbstractStrategy;
import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderCloseType;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.OfferSide;
import com.jeff.fx.common.TimeOfWeek;

public class TimeStrategy extends AbstractStrategy {
	
	private static Logger log = Logger.getLogger(TimeStrategy.class);

	private TimeOfWeek open = null;
	private TimeOfWeek close = null;
	private int stopLoss = 0;
	private int takeProfit = 0;
	private OfferSide offerSide = OfferSide.Ask;

	public TimeStrategy(int id, Map<String,Object> parameters) {
		
		super(id);
		
		stopLoss = (Integer)parameters.get("stopLoss");
		takeProfit = (Integer)parameters.get("takeProfit");
		open = (TimeOfWeek)parameters.get("open");
		close = (TimeOfWeek)parameters.get("close");
		offerSide = (OfferSide)parameters.get("offerSide");
	}
	
	public boolean isTestValid() {
		return (!open.equals(close));
	}
	
	public void execute(CandleCollection cc) {

		if(cc != null && cc.getStart() != null) {
			LocalDate date = cc.getStart();
			while(date.isBefore(cc.getEnd())) {
				executeWeek(cc.getCandleWeek(date));
				date = date.plusDays(7);
			}
		}
	}

	private void executeWeek(CandleWeek cw) {

		if(close.isBefore(open)) {
			return;
		}
		
		CandleDataPoint openCandle = cw.getCandle(open);
		CandleDataPoint closeCandle = cw.getCandle(close);
		
		if(openCandle == null || closeCandle == null) {
			return;
		}
		
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
		if(stopLoss > 0 && offerSide == OfferSide.Ask) { // find a SL on buy
			sl = cw.findNextLowBelowPrice(open, close, (float)order.getStopLossPrice(), offerSide);
		} else if(stopLoss > 0 && offerSide == OfferSide.Bid) { // find a SL on sell
			sl = cw.findNextHighAbovePrice(open, close, (float)order.getStopLossPrice(), offerSide);
		}
		
		// find a take profit
		if(takeProfit > 0 && offerSide == OfferSide.Ask) { // find a TP on buy
			tp = cw.findNextHighAbovePrice(open, close, (float)order.getTakeProfitPrice(), offerSide);
		} else if(takeProfit > 0 && offerSide == OfferSide.Bid) { // find a TP on sell
			tp = cw.findNextLowBelowPrice(open, close, (float)order.getTakeProfitPrice(), offerSide);
		}

		// find which comes first (sl, tp or close)
		if(sl == null && tp == null) {
			order.setClosePrice(offerSide == OfferSide.Ask ? closeCandle.getSellClose() : closeCandle.getBuyClose());
			order.setCloseType(OrderCloseType.Close);
			order.setCloseTime(closeCandle.getDate());
		} else if(sl != null && tp != null) {
			if(sl.getDate().isBefore(tp.getDate())) {
				order.setClosePrice(order.getStopLossPrice());
				order.setCloseType(OrderCloseType.StopLoss);
				order.setCloseTime(sl.getDate());
			} else {
				order.setClosePrice(order.getTakeProfitPrice());
				order.setCloseType(OrderCloseType.TakeProfit);
				order.setCloseTime(tp.getDate());
			}
		} else if(sl != null) {
			order.setClosePrice(order.getStopLossPrice());
			order.setCloseType(OrderCloseType.StopLoss);
			order.setCloseTime(sl.getDate());
		} else if(tp != null) {
			order.setClosePrice(order.getTakeProfitPrice());
			order.setCloseType(OrderCloseType.TakeProfit);
			order.setCloseTime(tp.getDate());
		}
		
		getOrderBook().close(order);
	}

	public void setOfferSide(OfferSide offerSide) {
		this.offerSide = offerSide;
	}
	
	public OfferSide getOfferSide() {
		return offerSide == null ? OfferSide.Ask : offerSide;
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
}
