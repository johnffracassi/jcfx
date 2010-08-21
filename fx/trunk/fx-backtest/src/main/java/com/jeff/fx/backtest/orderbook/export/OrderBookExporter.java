package com.jeff.fx.backtest.orderbook.export;

import java.io.File;
import java.io.IOException;

import com.jeff.fx.backtest.engine.OrderBook;

public interface OrderBookExporter {
	String getDescription();
	String getExtension();
	void export(OrderBook book, File file) throws IOException;
}
