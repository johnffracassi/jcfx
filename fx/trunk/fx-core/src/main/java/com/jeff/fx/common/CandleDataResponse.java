package com.jeff.fx.common;


public class CandleDataResponse extends FXDataResponse<CandleDataPoint> {
	
	private CandleCollection data;

	public CandleDataResponse() {
	}

	public CandleDataResponse(FXDataRequest request, CandleCollection data) {
		super(request);
		this.data = data;
	}

	public CandleCollection getCandles() {
		return data;
	}

	public void setCandles(CandleCollection data) {
		this.data = data;
	}
}
