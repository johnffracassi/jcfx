package com.siebentag.fx.tbv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.loader.CandleDataDAO;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component
public class TimeValueTableModel extends DefaultTableModel
{
	private List<TimeValueRow> rows;
	
	public void setCandleList(LocalDate startTime, List<CandleStickDataPoint> candles)
	{
		rows = TimeValueCalculator.calculateTimeValues(startTime, candles);
		fireTableDataChanged();
	}
	
	public Object getValueAt(int rowNum, int col)
	{
		TimeValueRow row = rows.get(rowNum);
		
		switch(col)
		{
			case 0: return row.getTime();
			case 1: return row.getSize();
			case 2: return row.getAverage();
			case 3: return row.getTop(0.1)*100.0;
			case 4: return row.getBottom(0.1)*100.0;
			case 5: return row.getRatio(0.1);
			case 6: return row.getTop(0.25)*100.0;
			case 7: return row.getBottom(0.25)*100.0;
			case 8: return row.getRatio(0.25);
			default: return "ERROR";
		}
	}
	
	public Class<?> getColumnClass(int col)
	{
		switch(col)
		{
			case 0: return LocalTime.class;
			case 1: return Integer.class;
			case 2: return Double.class;
			case 3: return Double.class;
			case 4: return Double.class;
			case 5: return Double.class;
			case 6: return Double.class;
			case 7: return Double.class;
			case 8: return Double.class;
			default: return String.class;
		}
	}
	
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}
	
	public int getRowCount()
	{
		return rows == null ? 0 : rows.size();
	}
	
	public int getColumnCount()
	{
		return 9;
	}
	
	public String getColumnName(int col)
	{
		switch(col)
		{
			case 0: return "Time";
			case 1: return "Count";
			case 2: return "Average";
			case 3: return "Top 10%";
			case 4: return "Bottom 10%";
			case 5: return "Ratio";
			case 6: return "Top 25%";
			case 7: return "Bottom 25%";
			case 8: return "Ratio";
			default: return "ERR";
		}
	}
	
	@Autowired
	private CandleDataDAO dao;
	public static void main(String[] args)
	{
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    TimeValueTableModel app = (TimeValueTableModel)ctx.getBean("timeValueTableModel");
	    app.run();
	}
	public void run()
	{
		List<CandleStickDataPoint> data = dao.findCandles(FXDataSource.GAIN, Instrument.AUDUSD, "900");
		TimeValueCalculator.calculateTimeValues(new LocalDate(2009, 1, 1), data);
	}
}

class TimeValueCalculator
{
	public static List<TimeValueRow> calculateTimeValues(LocalDate startDate, List<CandleStickDataPoint> candles)
	{
		 Map<LocalTime,List<Double>> values = new HashMap<LocalTime, List<Double>>();
		 Iterator<CandleStickDataPoint> candleIterator = candles.iterator();
		 CandleStickDataPoint candle = candleIterator.next();

		 if(startDate == null)
		 {
			 startDate = candle.getLocalDateTime().toLocalDate();
		 }
		 else
		 {
			 while(candleIterator.hasNext())
			 {
				 candle = candleIterator.next();
				 if(!candle.getLocalDateTime().isBefore(startDate.toLocalDateTime(new LocalTime(0,0))))
					 break;
			 }
		 }

		 // start counting from here...
		 while(candleIterator.hasNext())
		 {
			 // find candles for the day
			 List<CandleStickDataPoint> candlesForDay = new ArrayList<CandleStickDataPoint>(100);
			 while(candleIterator.hasNext() && candle.getLocalDateTime().toLocalDate().equals(startDate))
			 {
				 candlesForDay.add(candle);
				 candle = candleIterator.next();
			 }
			 
			 if(candlesForDay.size() > 0)
			 {
				 // sort candle by opening price
				 Collections.sort(candlesForDay, new Comparator<CandleStickDataPoint>() {
					public int compare(CandleStickDataPoint o1, CandleStickDataPoint o2) {
						return (o1.getBuyOpen() > o2.getBuyOpen()) ? 1 : ((o1.getBuyOpen() < o2.getBuyOpen()) ? -1 : 0);
					}
				 });
				 
				 // normalise
				 double high = candlesForDay.get(0).getBuyOpen();
				 double low = candlesForDay.get(candlesForDay.size() - 1).getBuyOpen();
				 double scale = 1.0 / (high - low);
				 
				 // add scores to map
				 for(CandleStickDataPoint c : candlesForDay)
				 {
					 double candleValue = (c.getBuyOpen() - low) * scale;
					 
					 if(!values.containsKey(c.getLocalDateTime().toLocalTime()))
					 {
						 List<Double> valueList = new ArrayList<Double>(100);
						 values.put(c.getLocalDateTime().toLocalTime(), valueList);
					 }
					 
					 values.get(c.getLocalDateTime().toLocalTime()).add(candleValue);
				 }
			 }
			 
			 startDate = candle.getLocalDateTime().toLocalDate();
		 }

		 // show scores for each time
		 List<TimeValueRow> rows = new ArrayList<TimeValueRow>();
		 for(LocalTime time : values.keySet())
		 {
			 List<Double> candleValues = values.get(time);
			 rows.add(new TimeValueRow(time, candleValues));
		 }
		 
		 return rows;
	}
} 

class TimeValueRow
{
	private LocalTime time;
	private List<Double> values;
	
	public TimeValueRow(LocalTime time, List<Double> values)
	{
		this.time = time;
		this.values = values;
	}
	
	public LocalTime getTime()
	{
		return time;
	}
	
	public double getRatio(double percentile)
	{
		return getTop(percentile) / getBottom(percentile);
	}
	
	public double getTop(double percentile)
	{
		return getPercentileCount((1.0 - percentile), true);
	}
	
	public double getBottom(double percentile)
	{
		return getPercentileCount(percentile, false);
	}
	
	public double getPercentileCount(double percentile, boolean above)
	{
		int count = 0;
		
		for(Double value : values)
		{
			if((value >= percentile && above) || (value <= percentile && !above))
			{
				count ++;
			}
		}
		
		return ((double)count / getSize());
	}
	
	
	public double getAverage()
	{
		return getSum() / getSize();
	}
	
	public double getSum()
	{
		double sum = 0.0;
		
		for(Double value : values)
		{
			sum += value;
		}
		
		return sum;
	}
	
	public int getSize()
	{
		return values.size();
	}
}







