package com.jeff.fx.backtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalDateTime;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.OfferSide;

public class SimpleStrategy {

	private int candleSkip = 20;
	private int candleCount = 0;
	private BTOrder currentOrder = null;
	
	public BTResult test(List<CandleDataPoint> candles, BTParameterSet param) {
		for(CandleDataPoint candle : candles) {
			candle(candle);
		}
	}

	private void candle(CandleDataPoint candle) {
		candleCount ++;
		
		if(candleCount % (candleSkip*2) == 0) {
			open(new BTOrder(), candle);
		} else if(candleCount % (candleSkip*2) == candleSkip) {
			close(currentOrder, candle);
		}
	}

	private void open(BTOrder order, CandleDataPoint candle) {
		order.setOpenTime(candle.getDate());
		order.setOpenPrice(order.getOfferSide() == OfferSide.Ask ? candle.getBuyOpen() : candle.getSellOpen());
		order.setId(1);
	}

	private void close(BTOrder order, CandleDataPoint candle) {
		order.setCloseTime(candle.getDate());
		order.setClosePrice(order.getOfferSide() == OfferSide.Ask ? candle.getSellOpen() : candle.getBuyOpen());
	}
}

class BTOrder {
	
	private int id;
	private OfferSide offerSide;
	private double units;
	private double openPrice;
	private double closePrice;
	private LocalDateTime openTime;
	private LocalDateTime closeTime;

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

class BTResult {

	private BTParameterSet parameterSet;

	public BTParameterSet getParameterSet() {
		return parameterSet;
	}

	public void setParameterSet(BTParameterSet parameterSet) {
		this.parameterSet = parameterSet;
	}
}

class BTParameterSet {

	private Map<String, BTParameterValueSet> params = new HashMap<String, BTParameterValueSet>();

	public int getPermutationCount() {

		int perms = 0;

		for (String key : params.keySet()) {

			BTParameterValueSet val = params.get(key);
			int steps = val.getNumberOfSteps();

			if (steps > 0) {
				perms = (perms == 0 ? steps : perms * steps);
			}
		}

		return perms;
	}
	
	public Set<String> getKeys() {
		return params.keySet();
	}

	public int getKeyCount() {
		return params.keySet().size();
	}
	
	public void setParameter(String key, BTParameterValueSet val) {
		params.put(key, val);
	}

	public void removeParameter(String key) {
		params.remove(key);
	}
}

class BTParameterTable {
	
	private double[][] values;
	
	public BTParameterTable(BTParameterSet params) {
		
		values = new double[params.getKeyCount()][params.getPermutationCount()];
			
		for(String key : params.getKeys()) {
			
		}
	}
	
}

class BTParameterValueSet {

	private double startValue;
	private double endValue;
	private double step;

	public int getNumberOfSteps() {
		return (int) ((endValue - startValue) / step);
	}

	public double getValue(int step) {
		return (startValue + ((endValue - startValue) / (double) step));
	}

	public double getStartValue() {
		return startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}
}
