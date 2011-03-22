package com.jeff.fx.backtest.engine;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.OfferSide;

public abstract class AbstractStrategy {
	
	/**
	 * unique test id (usually a reference to the parameter set) 
	 */
	private int id = -1;
	
	/**
	 * order book for this instance
	 */
	private OrderBook orderBook;

	/**
	 * indicator cache for this instance
	 */
	protected IndicatorCache indicators;

	/**
	 * @param id
	 */
	public AbstractStrategy(int id) {
		this.id = id;
		orderBook = new OrderBook();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isTestValid() {
		return true;
	}

	/**
	 * Execute the test with all strategies
	 * @param cc
	 */
	public abstract OrderBook execute(CandleCollection candles);
	
	/**
	 * 
	 * @param order
	 * @param candle
	 */
	protected void openOrder(BTOrder order, CandleDataPoint candle) {
		order.setInstrument(candle.getInstrument());
		order.setOpenTime(candle.getDateTime());
		order.setOpenPrice(order.getOfferSide() == OfferSide.Buy ? candle.getBuyOpen() : candle.getSellOpen());
		orderBook.post(order);
	}

	/**
	 * Check if the order has hit a stop loss or take profit
	 * 
	 * @param order
	 * @param candle
	 * @return
	 */
	protected final boolean isOrderStopped(BTOrder order, CandleDataPoint candle) {
		return getCloseType(order, candle) != OrderCloseType.Close;
	}
	
	/**
	 * Check the type of close method (normal, take profit or stop loss)
	 * 
	 * @param order
	 * @param candle
	 * @return
	 */
	protected final OrderCloseType getCloseType(BTOrder order, CandleDataPoint candle) {

		OrderCloseType type = OrderCloseType.Close;
		
		// check for a stop loss or take profit
		if(order.getOfferSide() == OfferSide.Buy) {
			if(order.hasStopLoss() && candle.getSellLow() < order.getStopLossPrice()) {
				type = (OrderCloseType.StopLoss);
			} else if(order.hasTakeProfit() && candle.getBuyHigh() > order.getTakeProfitPrice()) {
				type = (OrderCloseType.TakeProfit);
			}
		} else if(order.getOfferSide() == OfferSide.Sell) {
			if(order.hasTakeProfit() && candle.getBuyHigh() > order.getTakeProfitPrice()) {
				type = (OrderCloseType.TakeProfit);
			} else if(order.hasStopLoss() && candle.getSellLow() < order.getStopLossPrice()) {
				type = (OrderCloseType.StopLoss);
			}
		}
		
		return type;
	}

	protected final double getClosePrice(BTOrder order, CandleDataPoint candle) {

		double closePrice = order.getOfferSide() == OfferSide.Buy ? candle.getSellOpen() : candle.getBuyOpen();
		
		// check for a stop loss or take profit
		if(order.getOfferSide() == OfferSide.Buy) {
			if(order.hasStopLoss() && candle.getSellLow() < order.getStopLossPrice()) {
				closePrice = order.getStopLossPrice();
			} else if(order.hasTakeProfit() && candle.getBuyHigh() > order.getTakeProfitPrice()) {
				closePrice = order.getTakeProfitPrice();
			}
		} else if(order.getOfferSide() == OfferSide.Sell) {
			if(order.hasTakeProfit() && candle.getBuyHigh() > order.getTakeProfitPrice()) {
				closePrice = order.getTakeProfitPrice();
			} else if(order.hasStopLoss() && candle.getSellLow() < order.getStopLossPrice()) {
				closePrice = order.getStopLossPrice();
			}
		}
		
		return closePrice;
	}

	/**
	 * Close an order. Sets prices taking stops/takes into consideration
	 * 
	 * @param order
	 * @param candle
	 */
	protected void closeOrder(BTOrder order, CandleDataPoint candle) {

		order.setClosePrice(getClosePrice(order, candle));
		order.setCloseType(getCloseType(order, candle));
		order.setCloseTime(candle.getDateTime());

		orderBook.close(order);
	}
	
	public final OrderBook getOrderBook() {
		return orderBook;
	}
	
	public final boolean hasOpenOrder() {
		return orderBook.hasOpenOrders();
	}

	public final void setId(int id) {
		this.id = id;
	}
	
	public final int getId() {
		return id;
	}
}
