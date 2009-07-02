package com.siebentag.fx.backtest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderBookReport
{
	public static void saveReport(BalanceSheet balanceSheet, File file) throws IOException
	{
		file.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
	
		out.println("<html><head><link href=../../../style.css type=text/css rel=stylesheet></head><body>");
		out.println("<table border=0 cellspacing=0>");
		
		out.println("<tr>");
		out.println("<th>#</th>");
		out.println("<th>Side</th>");
		out.printf("<th class=num>Lots</th>");
		out.printf("<th colspan=2 class=num>Open</th>");
		out.printf("<th colspan=2 class=num>Close</th>");
		out.printf("<th>PIPS</th>");
		out.printf("<th>P/L</th>");
		out.printf("<th>Balance</th>");
		out.println("</tr>");
		
		double balance = 10000.0;
		
		int i=1; 
		for(Order order : balanceSheet.getClosedOrders())
		{
			balance += order.getProfitLoss();
			
			out.println("<tr>");
			out.println("<td>" + (i++) + "</td>");
			out.println("<td>" + order.getSide() + "</td>");
			out.printf("<td class=num>%.1f</td>", order.getLots());
			out.printf("<td class=num>%.4f/%2.0f</td>", order.getOpen().getSell(), (order.getOpen().getBuy()*10000.0)%100.0);
			out.println("<td>" + (order.getOpen().getDate()) + "</td>");
			out.printf("<td class=num>%.4f/%2.0f</td>", order.getClose().getSell(), (order.getClose().getBuy()*10000.0)%100.0);
			out.println("<td>" + (order.getClose().getDate()) + "</td>");
			out.printf("<td class=%s>%.1f</td>", (order.getProfitLoss()>0?"profit":"loss"), order.getPipProfitLoss());
			out.printf("<td class=%s>%.2f</td>", (order.getProfitLoss()>0?"profit":"loss"), order.getProfitLoss());
			out.printf("<td>%.2f</td>", balance);
			out.println("</tr>");
		}
		
		out.println("</table>");
		out.println("</body></html>");
		
		out.flush();
		out.close();
	}
}
