package com.siebentag.fx.mv;

import java.util.Date;

class TickAggregationRow {

	public double getHighSell() {
		return highSell;
	}

	public void setHighSell(double highSell) {
		this.highSell = highSell;
	}

	public double getHighBuy() {
		return highBuy;
	}

	public void setHighBuy(double highBuy) {
		this.highBuy = highBuy;
	}

	private Date startDate;
	private Date endDate;

	private double openBuy;
	private double closeSell;
	private double lowSell;
	private double highSell;

	private double openSell;
	private double closeBuy;
	private double lowBuy;
	private double highBuy;

	
	public double getProfitLong()
	{
		return 10000.0 * (closeSell - openBuy);
	}

	public double getProfitShort()
	{
		return 10000.0 * (openSell - closeBuy);
	}
	
	public double getDrawdownLong() {
		return 10000.0 * (lowSell - openBuy);
	}
	
	public double getDrawdownShort() {
		return 10000.0 * (highBuy - openSell);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getOpenBuy() {
		return openBuy;
	}

	public void setOpenBuy(double openBuy) {
		this.openBuy = openBuy;
	}

	public double getCloseSell() {
		return closeSell;
	}

	public void setCloseSell(double closeSell) {
		this.closeSell = closeSell;
	}

	public double getLowSell() {
		return lowSell;
	}

	public void setLowSell(double lowSell) {
		this.lowSell = lowSell;
	}

	public double getOpenSell() {
		return openSell;
	}

	public void setOpenSell(double openSell) {
		this.openSell = openSell;
	}

	public double getCloseBuy() {
		return closeBuy;
	}

	public void setCloseBuy(double closeBuy) {
		this.closeBuy = closeBuy;
	}

	public double getLowBuy() {
		return lowBuy;
	}

	public void setLowBuy(double lowBuy) {
		this.lowBuy = lowBuy;
	}
}
