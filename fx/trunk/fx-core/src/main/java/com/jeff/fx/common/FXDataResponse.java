package com.jeff.fx.common;


public class FXDataResponse<T extends FXDataPoint> {
	
	private FXDataRequest request;

	public FXDataResponse() {
	}

	public FXDataResponse(FXDataRequest request) {
		this.request = request;
	}

	public FXDataRequest getRequest() {
		return request;
	}

	public void setRequest(FXDataRequest request) {
		this.request = request;
	}
}
