package com.siebentag.fx.backtest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StrategySummaryReport
{
	public static void saveReport(List<Strategy> strategies, File file) throws IOException
	{
		file.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
	
		out.println("<html><head><link href=../../../style.css type=text/css rel=stylesheet></head><body>");
		out.println("<table border=0 cellspacing=0>");

		out.println("<tr>");
		out.println("<th>#</th>");
		out.println("<th>Strategy</th>");
		out.println("<th class=num>Orders</th>");
		out.println("<th class=num>Avg P/L</th>");
		out.println("<th class=num>Profit</th>");
		out.println("<th class=num>Drawdown</th>");
		out.println("<th class=num>Prof/DD</th>");
		out.println("<th class=num>Balance</th>");
		out.println("<th class=num>Wins</th>");
		out.println("<th class=num>Losses</th>");
		out.println("<th class=num>Win%</th>");
		out.println("</tr>");
		
		Collections.sort(strategies, new Comparator<Strategy>() {
			public int compare(Strategy o1, Strategy o2) {
				return o1.getBalanceSheet().getBalance() > o2.getBalanceSheet().getBalance() ? -1 : 1;
			}
		});
		
		int i=1;
		for(Strategy strategy : strategies)
		{
			int orderCount = strategy.getBalanceSheet().orderCount();
			double profit = strategy.getBalanceSheet().calculateProfitForAllOrders();
			double avgPL = orderCount == 0 ? 0.0 : profit / orderCount;
			double drawdown = strategy.getBalanceSheet().calculateMaximumDrawdown();
			double balance = strategy.getBalanceSheet().calculateBalance();
			int wins = strategy.getBalanceSheet().wins();
			int losses = strategy.getBalanceSheet().losses();
			
			out.println("<tr>");
			out.printf("<td>%1d</td>", i++);
			out.printf("<td><a href=./%s.html>%s</a></td>", strategy.getName(), strategy.getName());
			out.printf("<td class=num>%1d</td>", orderCount);
			out.printf("<td class=num>%.2f</td>", avgPL);
			out.printf("<td class=num>%.2f</td>", profit);
			out.printf("<td class=num>%.2f</td>", drawdown);
			out.printf("<td class=num>%.2f</td>", profit / drawdown);
			out.printf("<td class=num>%.2f</td>", balance);
			out.printf("<td class=num>%1d</td>", wins);
			out.printf("<td class=num>%1d</td>", losses);
			out.printf("<td class=num>%3.1f</td>", (double)wins / (double)orderCount * 100.0);
			out.println("</tr>");
		}
		
		out.println("</table>");
		out.println("</body></html>");
		
		out.flush();
		out.close();
	}
}
