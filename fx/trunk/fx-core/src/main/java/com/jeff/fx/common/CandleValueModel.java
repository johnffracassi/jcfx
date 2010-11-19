package com.jeff.fx.common;

public enum CandleValueModel {

	AverageOfHL(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh + bl + sh + sl) / 4.0f;
		};
	}),

	AverageOfOHLC(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh + bl + bo + bc + so + sh + sl + sc) / 8.0f;
		};
	}),
	
	Typical(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh + bl + bc) / 3.0f;
		};
	}),
	
    Open(new Evaluator() {
        public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
            return (bo + so) / 2.0f;
        };
    }),
    
    Close(new Evaluator() {
        public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
            return (bc + sc) / 2.0f;
        };
    }),
    
    High(new Evaluator() {
        public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
            return (bh + sh) / 2.0f;
        };
    }),
    
	Low(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bl + sl) / 2.0f;
		};
	}),
	
	SellOpen(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (so);
		};
	}),
	
	SellClose(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (sc);
		};
	}),
	
	SellHigh(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (sh);
		};
	}),
	
	SellLow(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (sl);
		};
	}),
	
	BuyOpen(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bo);
		};
	}),
	
	BuyClose(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bc);
		};
	}),
	
	BuyHigh(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bh);
		};
	}),
	
	BuyLow(new Evaluator() {
		public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
			return (bl);
		};
	});
	
	private Evaluator evaluator;

	private CandleValueModel(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	public float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc) {
		return evaluator.evaluate(bo, bh, bl, bc, so, sh, sl, sc);
	}
	
	public float evaluate(CandleDataPoint dp) {
		return evaluator.evaluate(dp);
	}
}

abstract class Evaluator {
	
	float evaluate(CandleDataPoint dp) {
		return evaluate((float)dp.getBuyOpen(), (float)dp.getBuyHigh(), (float)dp.getBuyLow(), (float)dp.getBuyClose(), (float)dp.getSellOpen(), (float)dp.getSellHigh(), (float)dp.getSellLow(), (float)dp.getSellClose());
	}
	
	abstract float evaluate(float bo, float bh, float bl, float bc, float so, float sh, float sl, float sc);
}