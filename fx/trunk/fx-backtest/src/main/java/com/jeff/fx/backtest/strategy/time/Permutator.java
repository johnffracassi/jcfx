package com.jeff.fx.backtest.strategy.time;

public class Permutator {
	
	private int[] perms;
	private int[] repetitions;
	
	public Permutator(int[] perms) {
		this.perms = perms;
		calculateRepetitions();
	}
	
	private void calculateRepetitions() {
		repetitions = new int[perms.length];
		repetitions[0] = 1;
		
		for(int i=0; i<perms.length - 1; i++) {
			repetitions[i+1] = repetitions[i] * perms[i];
		}
	}
	
	public int getPermutationCount() {
		int count = 1;
		for(int i=0; i<perms.length; i++) {
			count *= perms[i]; 
		}
		return count;
	}
	
	public int[] getPermutation(int row) {
		int[] params = new int[perms.length];
		for(int col=0; col<perms.length; col++) {
			params[col] = getPermutation(row, col);
		}
		return params;
	}
	
	private int getPermutation(int row, int col) {
		int j = row / repetitions[col];
		int k = j % perms[col];
		return k;
	}
}











