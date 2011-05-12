package com.jeff.fx.common;

public enum CandleValueModel {

	AverageOfHL(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh + bl + sh + sl) / 4.0f;
		};
	}),

	AverageOfOHLC(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh + bl + bo + bc + so + sh + sl + sc) / 8.0f;
		};
	}),
	
	Typical(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh + bl + bc) / 3.0f;
		};
	}),
	
    Open(new CandleValueModelEvaluator() {
        public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
            return (bo + so) / 2.0f;
        };
    }),
    
    Close(new CandleValueModelEvaluator() {
        public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
            return (bc + sc) / 2.0f;
        };
    }),
    
    High(new CandleValueModelEvaluator() {
        public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
            return (bh + sh) / 2.0f;
        };
    }),
    
	Low(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bl + sl) / 2.0f;
		};
	}),
	
	SellOpen(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (so);
		};
	}),
	
	SellClose(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (sc);
		};
	}),
	
	SellHigh(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (sh);
		};
	}),
	
	SellLow(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (sl);
		};
	}),
	
	BuyOpen(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bo);
		};
	}),
	
	BuyClose(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bc);
		};
	}),
	
	BuyHigh(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh);
		};
	}),
	
	BuyLow(new CandleValueModelEvaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bl);
		};
	});
	
	private CandleValueModelEvaluator evaluator;

	private CandleValueModel(CandleValueModelEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
		return evaluator.evaluate(bo, bh, bl, bc, so, sh, sl, sc);
	}
	
	public float evaluate(CandleDataPoint dp) {
		return evaluator.evaluate(dp);
	}
}

abstract class CandleValueModelEvaluator
{
	float evaluate(CandleDataPoint dp) {
		return evaluate((float)dp.getBuyOpen(), (float)dp.getBuyHigh(), (float)dp.getBuyLow(), (float)dp.getBuyClose(), (float)dp.getSellOpen(), (float)dp.getSellHigh(), (float)dp.getSellLow(), (float)dp.getSellClose());
	}
	
	abstract float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc);
}