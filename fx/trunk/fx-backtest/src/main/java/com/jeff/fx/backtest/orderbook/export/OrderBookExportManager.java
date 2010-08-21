package com.jeff.fx.backtest.orderbook.export;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.engine.OrderBook;

@Component
public class OrderBookExportManager {

	@Autowired
	private List<OrderBookExporter> exporters;
	
	public void export(String format, OrderBook book, File file) throws IOException {
		export(getExporter(format), book, file);
	}
	
	public OrderBookExporter getExporter(String format) {
		for(OrderBookExporter exporter : exporters) {
			if(exporter.getExtension().equalsIgnoreCase(format)) {
				return exporter;
			}
		}
		return null;
	}
	
	public void export(OrderBookExporter exporter, OrderBook book, File file) throws IOException {
		exporter.export(book, file);
	}
	
	public List<OrderBookExporter> getExporters() {
		return exporters;
	}
}
