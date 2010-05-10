package com.jeff.fx.backtest.engine;

public class BTParameterTable {
	
	public static double[][] getParameterValueTable(BTParameterSet ps) {
		
		// work out the number of values for each parameter
		int[] cycles = new int[ps.getKeyCount()];
		for(int i=0; i<cycles.length; i++) {
			cycles[i] = ps.getValueSet(i).getNumberOfSteps();
		}
		
		// how many times does each values need to be repeated during the normalisation
		int[] repeats = new int[ps.getKeyCount()];
		repeats[repeats.length-1] = 1;
		for(int i=repeats.length-2; i>=0; i--) {
			repeats[i] = repeats[i+1] * cycles[i+1];
		}

		// calculate the total number of permutations
		int permutations = 1;
		for(int i=0; i<cycles.length; i++) {
			permutations *= cycles[i];
		}
		
		// create local copies of the parameter value sets
		BTParameterValueSet[] pvs = new BTParameterValueSet[ps.getKeyCount()];
		for(int i=0; i<pvs.length; i++) {
			pvs[i] = ps.getValueSet(i);
		}
		
		// build the normalised parameter values table
		double[][] data = new double[cycles.length][permutations];
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
}