package com.jeff.fx.backtest.orderbook.export;

import org.springframework.stereotype.Component;

@Component
public class TSVExporter extends CSVExporter {

	public String getDescription() {
		return "Tab Separated Values";
	}
	
	@Override
	public String getExtension() {
		return "tsv";
	}
	
	protected char getSeparator() {
		return '\t';
	}
}
