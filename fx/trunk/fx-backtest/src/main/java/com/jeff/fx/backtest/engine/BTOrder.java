package com.jeff.fx.backtest.engine;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.OfferSide;

public class BTOrder {
	
	private int id;
	private OfferSide offerSide;
	private double units;
	private double openPrice;
	private double closePrice;
	private LocalDateTime openTime;
	private LocalDateTime closeTime;

	public double getProfit() {
		if(offerSide == OfferSide.Ask) {
			return 10000.0 * (getClosePrice() - getOpenPrice());
		} else {
			return 10000.0 * (getOpenPrice() - getClosePrice());
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
}
