package com.jeff.fx.common;

import java.util.List;

public class FXDataResponse<T extends FXDataPoint> {
	
	private FXDataRequest request;
	private List<T> data;

	public FXDataResponse() {
	}

	public FXDataResponse(FXDataRequest request, List<T> data) {
		this.data = data;
		this.request = request;
	}

	public FXDataRequest getRequest() {
		return request;
	}

	public void setRequest(FXDataRequest request) {
		this.request = request;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
