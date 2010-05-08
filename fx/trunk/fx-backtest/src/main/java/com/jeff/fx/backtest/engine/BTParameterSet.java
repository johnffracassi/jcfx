package com.jeff.fx.backtest.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BTParameterSet {

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
