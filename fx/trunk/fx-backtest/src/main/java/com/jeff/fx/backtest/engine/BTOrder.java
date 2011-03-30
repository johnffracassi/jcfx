package com.jeff.fx.backtest.engine;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.OfferSide;

public class BTOrder {
	
	private int id;
	private Instrument instrument;
	private OfferSide offerSide;
	private LocalDateTime openTime;
	private LocalDateTime closeTime;
	private OrderCloseType closeType = OrderCloseType.Open;
	private double units;
	private double openPrice;
	private double closePrice;
	private int stopLoss;
	private int takeProfit;

    private double pipMult = 1.0;

	public int getProfit() {
		
		if(offerSide == OfferSide.Buy) {
			return (int)StrictMath.round((pipMult * (getClosePrice() - getOpenPrice())));
		} else {
			return (int)StrictMath.round((pipMult * (getOpenPrice() - getClosePrice())));
		}
	}

	public double getStopLossPrice() {
		
		if(stopLoss == 0) {
			return 0.0;
		}
		
		if(offerSide == OfferSide.Buy) {
			return getOpenPrice() - (stopLoss * instrument.getPipValue());
		} else {
			return getOpenPrice() + (stopLoss * instrument.getPipValue());
		}
	}

	public void setStopLossPrice(double newPrice) {
		
		if(newPrice == 0.0) {
			stopLoss = 0;
		}
		
		if(offerSide == OfferSide.Buy) {
			setStopLoss((int)StrictMath.round(((getOpenPrice() - newPrice) / instrument.getPipValue())));
		} else {
			setStopLoss((int)StrictMath.round(((newPrice - getOpenPrice()) / instrument.getPipValue())));
		}
	}

	public double getTakeProfitPrice() {
		
		if(takeProfit == 0) {
			return 0.0;
		}
		
		if(offerSide == OfferSide.Buy) {
			return getOpenPrice() + (takeProfit * instrument.getPipValue());
		} else {
			return getOpenPrice() - (takeProfit * instrument.getPipValue());
		}
	}
	
	public void setTakeProfitPrice(double newPrice) {
		
		if(newPrice == 0.0) {
			takeProfit = 0;
		}
		
		if(offerSide == OfferSide.Buy) {
			setTakeProfit((int)StrictMath.round(((newPrice - getOpenPrice()) / instrument.getPipValue())));
		} else {
			setTakeProfit((int)StrictMath.round(((getOpenPrice() - newPrice) / instrument.getPipValue())));
		}
	}
	
	public OfferSide getOfferSide() {
		return offerSide;
	}

	public void setOfferSide(OfferSide offerSide) {
		this.offerSide = offerSide;
	}

	public double getUnits() {
		return units;
	}

	public void setUnits(double units) {
		this.units = units;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(LocalDateTime openTime) {
		this.openTime = openTime;
	}

	public LocalDateTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalDateTime closeTime) {
		this.closeTime = closeTime;
	}

	public double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
        this.pipMult = 1.0 / instrument.getPipValue();
	}

	public boolean hasTakeProfit() {
		return takeProfit > 0.0;
	}
	
	public boolean hasStopLoss() {
		return stopLoss > 0.0;
	}

	public OrderCloseType getCloseType() {
		return closeType;
	}

	public void setCloseType(OrderCloseType closeType) {
		this.closeType = closeType;
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
}
