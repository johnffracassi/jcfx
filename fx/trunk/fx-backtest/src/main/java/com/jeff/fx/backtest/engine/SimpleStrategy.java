package com.jeff.fx.backtest.engine;

import java.util.ArrayList;
import java.util.List;

import com.jeff.fx.common.CandleDataPoint;

public class SimpleStrategy extends AbstractStrategy {
	
	private int stayClosedFor = 0;
	private int stayOpenFor = 0;
	private int candleCount = 0;
	private int totalCandleCount = 0;

	
	public SimpleStrategy(int id, double[] params) {
		super(id);
		stayClosedFor = (int)params[0];
		stayOpenFor = (int)params[1];
	}
	
	public static List<SimpleStrategy> createTestSet(BTParameterSet ps) {
		
		double[][] parameters = BTParameterTable.getParameterValueTable(ps);
		int permutations = parameters[0].length;
		
		List<SimpleStrategy> list = new ArrayList<SimpleStrategy>(permutations);
		for(int i=0; i<permutations; i++) {
			SimpleStrategy strategy = new SimpleStrategy(i+1, new double[] { parameters[0][i], parameters[1][i] });
			list.add(strategy);
		}
		
		return list;
	}
	
	public void candle(CandleDataPoint candle) {
		
		totalCandleCount ++;
		candleCount ++;
		
		if(!hasOpenOrder() && candleCount == stayClosedFor) {
			open(new BTOrder(), candle);
			candleCount = 0;
		} else if(hasOpenOrder() && candleCount == stayOpenFor) {
			close(candle);
			candleCount = 0;
		}
	}


	@Override
	public String toString() {
		return getId() + ". " + stayOpenFor + "/" + stayClosedFor + " = " + getBalance() + " (" + getWins() + "/" + getLosses() + ")";
	}
}
