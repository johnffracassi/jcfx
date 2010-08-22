package com.jeff.fx.backtest.orderbook.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.engine.BTOrder;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.util.DateUtil;

@Component
public class CSVExporter implements OrderBookExporter {

	private static char SEPARATOR = ','; 
	private static char NEW_LINE = '\n';
	
	public String getDescription() {
		return "Comma Separated Values";
	}
	
	public String getExtension() {
		return "csv";
	}
	
	public void export(OrderBook book, File file) throws IOException {
		
		FileOutputStream out = null;
		char separator = getSeparator();

		try {
			out = new FileOutputStream(file);

			StringBuffer header = new StringBuffer();
			header.append("Id").append(separator); 
			header.append("OfferSide").append(separator);
			header.append("Instrument").append(separator);
			header.append("Units").append(separator);
			header.append("OpenDate").append(separator);
			header.append("OpenTime").append(separator);
			header.append("OpenPrice").append(separator);
			header.append("CloseDate").append(separator);
			header.append("CloseTime").append(separator);
			header.append("ClosePrice").append(separator);
			header.append("StopLossPrice").append(separator);
			header.append("TakeProfitPrice");
			header.append(NEW_LINE);
			out.write(header.toString().getBytes());

			for(BTOrder order : book.getClosedOrders()) {
				
				StringBuffer buf = new StringBuffer();
				buf.append(order.getId()).append(separator);
				buf.append(order.getOfferSide()).append(separator);
				buf.append(order.getInstrument()).append(separator);
				buf.append(order.getUnits()).append(separator);
				buf.append(DateUtil.formatDate(order.getOpenTime())).append(separator);
				buf.append(DateUtil.formatTime(order.getOpenTime())).append(separator);
				buf.append(String.format("%.4f", order.getOpenPrice())).append(separator);
				buf.append(DateUtil.formatDate(order.getCloseTime())).append(separator);
				buf.append(DateUtil.formatTime(order.getCloseTime())).append(separator);
				buf.append(String.format("%.4f", order.getClosePrice())).append(separator);
				buf.append(String.format("%.4f", order.getStopLossPrice())).append(separator);
				buf.append(String.format("%.4f", order.getTakeProfitPrice()));
				buf.append(NEW_LINE);
				
				out.write(buf.toString().getBytes());
			}
			
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	protected char getSeparator() {
		return SEPARATOR;
	}
}
