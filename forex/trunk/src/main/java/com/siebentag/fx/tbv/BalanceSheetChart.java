package com.siebentag.fx.tbv;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.siebentag.fx.backtest.BalanceSheet;
import com.siebentag.fx.backtest.Order;

public class BalanceSheetChart extends JXPanel
{
	private BalanceSheet data;
	private ChartPanel pnlChart;
	
	public BalanceSheetChart()
	{
		setLayout(new BorderLayout());
		pnlChart = new ChartPanel(updateChart());
		add(pnlChart, BorderLayout.CENTER);
	}
	
	public void setBalanceSheet(BalanceSheet balanceSheet)
	{
		data = balanceSheet;
		pnlChart.setChart(updateChart());
	}
	
	private JFreeChart updateChart()
	{
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Balance Chart",           // chart title
                "Date",                    // domain axis label
                "Balance",                 // range axis label
                buildBalanceDataSet(),     // data
                false,                     // include legend
                true,                      // tooltips
                false                      // urls
            );
        
        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        
        
        // par value series
        final NumberAxis priceAxis = new NumberAxis("Par Value");
        priceAxis.setRange(new Range(0.0, 20000.0));
        plot.setRangeAxis(1, priceAxis);
        plot.setDataset(1, buildPriceDataSet());
        plot.mapDatasetToRangeAxis(1, 1);
        
        XYSplineRenderer renderer1 = new XYSplineRenderer();
        renderer1.setSeriesShapesVisible(0, false);
        renderer1.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(0, renderer1);

        XYSplineRenderer renderer2 = new XYSplineRenderer();
        renderer2.setSeriesShapesVisible(0, false);
        renderer2.setSeriesStroke(0, new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] {4.0f, 4.0f}, 0.0f));
        plot.setRenderer(1, renderer2);
        
        // balance axis
        final NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis(0);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(new Range(0.0, 20000.0));
       
        return chart;
    }
	
	private XYDataset buildPriceDataSet()
	{
		TimeSeries series = new TimeSeries("Par Value", Day.class);

		if(data != null && data.getOrders().size() > 0)
		{
			double openPrice = data.getOrders().get(0).getOpenPrice();
			double units = (data.getOrders().get(0).getLots() * 100000.0) / openPrice;
			
			for(Order order : data.getOrders())
			{
				double price = order.getOpenPrice();
				double balance = 10000.0 + (units * (price - openPrice));
				series.add(new Day(order.getOpen().getDate()), balance);
			}
		}
		
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		tsc.addSeries(series);
		
		return tsc;
	}
	
	private XYDataset buildBalanceDataSet()
	{
		TimeSeries series = new TimeSeries("Balance", Day.class);
		
		if(data != null)
		{
			double balance = 10000.0;
			for(Order order : data.getOrders())
			{
				balance += order.getProfitLoss();
				series.add(new Day(order.getOpen().getDate()), balance);
			}
		}
		
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		tsc.addSeries(series);
		
		return tsc;
	}
}
