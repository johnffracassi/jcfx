package com.jeff.fx.backtest;

import java.util.ArrayList;
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
	
	public static void main(String[] args) {
		
		BTParameterSet pbs = new BTParameterSet();
		
		BTParameterValueSet pvs1 = new BTParameterValueSet();
		pvs1.setStartValue(0.01);
		pvs1.setEndValue(0.25);
		pvs1.setStep(0.02);
		
		BTParameterValueSet pvs2 = new BTParameterValueSet();
		pvs2.setStartValue(1);
		pvs2.setEndValue(5);
		pvs2.setStep(1);
		
		BTParameterValueSet pvs3 = new BTParameterValueSet();
		pvs3.setStartValue(1);
		pvs3.setEndValue(2);
		pvs3.setStep(.5);
		
		pbs.setParameter("p1", pvs1);
		pbs.setParameter("p2", pvs2);
		pbs.setParameter("p3", pvs3);
		
		System.out.println(pbs);
		
		double[][] valueTable = getParameterValueTable(pbs);
	}
	
	public static double[][] getParameterValueTable(BTParameterSet ps) {
		
		int[] cycles = new int[ps.getKeyCount()];
		for(int i=0; i<cycles.length; i++) {
			cycles[i] = ps.getValueSet(i).getNumberOfSteps();
		}
		
		int[] repeats = new int[ps.getKeyCount()];
		repeats[repeats.length-1] = 1;
		for(int i=repeats.length-2; i>=0; i--) {
			repeats[i] = repeats[i+1] * cycles[i+1];
		}

		int permutations = cycles[0] * cycles[1] * cycles[2];
		double[][] data = new double[cycles.length][permutations];
		
		BTParameterValueSet[] pvs = new BTParameterValueSet[ps.getKeyCount()];
		for(int i=0; i<pvs.length; i++) {
			pvs[i] = ps.getValueSet(i);
		}
		
		for(int pvsIdx=0; pvsIdx<cycles.length; pvsIdx++) {
			int permutation = 0;
			int idxInDataset = 0;
			double[] dataset = pvs[pvsIdx].expand();

			while(permutation < permutations) {
				
				for(int repeat=0; repeat<repeats[pvsIdx]; repeat++) {
					data[pvsIdx][permutation] = dataset[idxInDataset];
					permutation++;
				}

				idxInDataset ++;
				if(idxInDataset == cycles[pvsIdx]) {
					idxInDataset = 0;
				}
			}
		}
		
		return data;
	}
	
	public BTResult test(List<CandleDataPoint> candles, BTParameterSet param) {
		for(CandleDataPoint candle : candles) {
			candle(candle);
		}
		return new BTResult();
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

class BTParameterTable {
	public BTParameterTable(BTParameterSet ps) {
	}
}

class BTParameterSet {

	private List<String> keys = new ArrayList<String>(10);
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
	
	public BTParameterValueSet getValueSet(int idx) {
		return params.get(keys.get(idx));
	}
	
	public BTParameterValueSet getValueSet(String key) {
		return params.get(key);
	}
	
	public List<String> getKeys() {
		return keys;
	}

	public int getKeyCount() {
		return keys.size();
	}
	
	public void setParameter(String key, BTParameterValueSet val) {
		params.put(key, val);
		keys.add(key);
	}

	public void removeParameter(String key) {
		params.remove(key);
		keys.remove(key);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		for(String key : keys) {
			buf.append(key + "=" + params.get(key) + "\n");
		}
		
		return buf.toString();
	}
}

class BTParameterValueSet {

	private double startValue;
	private double endValue;
	private double step;

	public int getNumberOfSteps() {
		return (int) ((endValue - startValue) / step) + 1;
	}

	public double[] expand() {
		double[] values = new double[getNumberOfSteps()];
		
		for(int i=0; i<values.length; i++) {
			values[i] = startValue + ((double)i * (double)step);
		}
		
		return values;
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
	
	@Override
	public String toString() {
		return "[" + startValue + "=>" + endValue + " s=" + step + " " + toString(expand()) + "]"; 
	}
	
	private String toString(double[] vals) {
		String str = "";
		for(int i=0; i<vals.length; i++) {
			str += String.valueOf(vals[i]);
			if(i <vals.length-1) {
				str += ", ";
			}
		}
		return "<" + str + ">";
	}
}
