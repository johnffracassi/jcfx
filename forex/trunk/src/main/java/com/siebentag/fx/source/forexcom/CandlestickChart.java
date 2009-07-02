package com.siebentag.fx.source.forexcom;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.ui.RefineryUtilities;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.loader.CandleDataDAO;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component
public class CandlestickChart extends JFrame
{
	@Autowired
	private CandleDataDAO dao;
	
	public void run(List<CandleStickDataPoint> candles)
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Candle sticks");
		final DefaultHighLowDataset dataset = convertCandlesToDataset(candles);
		final JFreeChart chart = createChart(dataset);
		chart.getXYPlot().setOrientation(PlotOrientation.VERTICAL);
		chart.getXYPlot().getRangeAxis(0).setRange(0.79, 0.83);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
		
	}

	public DefaultHighLowDataset convertCandlesToDataset(List<CandleStickDataPoint> candles)
	{
		Date[]   dates = new Date[candles.size()];
		double[] open  = new double[candles.size()];
		double[] high  = new double[candles.size()];
		double[] low   = new double[candles.size()];
		double[] close = new double[candles.size()];
		double[] vol   = new double[candles.size()];
		
		for(int i=0; i<candles.size(); i++)
		{
			CandleStickDataPoint candle = candles.get(i);
			dates[i] = candle.getDate();
			open[i] = candle.getBuyOpen();
			high[i] = candle.getBuyHigh();
			low[i] = candle.getBuyLow();
			close[i] = candle.getBuyClose();
			vol[i] = 0;
		}
		
		DefaultHighLowDataset dataset = new DefaultHighLowDataset("candles", dates, high, low, open, close, vol);
		
		return dataset;
	}
	
	private JFreeChart createChart(final DefaultHighLowDataset dataset)
	{
		final JFreeChart chart = ChartFactory.createCandlestickChart("Candlestick Demo", "Time", "Value", dataset, true);
		return chart;
	}

	public static void main(final String[] args)
	{
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    CandlestickChart app = (CandlestickChart)ctx.getBean("candlestickChart");

		List<CandleStickDataPoint> candles = CandleDataDAO.findCandles(FXDataSource.Forexite, Instrument.AUDUSD, "900", new LocalDate(2009,6,1), new LocalDate(2009,6,6));

	    app.run(candles);
		
	    app.pack();
		RefineryUtilities.centerFrameOnScreen(app);
		app.setVisible(true);
	}
}
