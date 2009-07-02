 package com.siebentag.fx.strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("timeBased")
public class TimeBased
{
	@Autowired
	private DataSource dataSource;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String[] pairs = {
		 "AUDUSD",        
		 "EURCHF",        
		 "EURGBP",        
		 "EURJPY",        
		 "EURUSD",        
		 "GBPAUD",        
		 "GBPJPY",        
		 "GBPUSD",        
		 "USDCHF",        
		 "USDJPY"   
	};
	
	public static void main(String[] args)
    {
		
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    TimeBased app = (TimeBased)ctx.getBean("timeBased");
	    app.run();
    }
	
	public void run()
	{
		try
		{
			for(String instrument : pairs)
			{
//			String instrument = pairs[7];
			
				Calendar cal = Calendar.getInstance();
				cal.set(2008, Calendar.AUGUST, 31);
				
				DeltaTableCollection dtc = loadDeltaTables("GAIN", cal.getTime(), 222, instrument);
				Set<String> keys = dtc.getKeys();
				
				List<String> keyList = new ArrayList<String>(keys);
				Collections.sort(keyList);
			
				PrintWriter pw = new PrintWriter(new FileOutputStream(new File(instrument + "-5m.tsv")));
				
				pw.println("t1\tt2\tperiod\tsum\tavg\tavg/ppm\tsize\tstdev\tmedian\trange\tmin\tmax\tpos\tpos%\tneg\tneg%\t10%\t20%\t30%\t40%\t50%\t60%\t70%\t80%\t90%");
				for(String t1 : keyList)
				{
					for(String t2 : keyList)
					{
						if(t1.compareTo(t2) < 0)
						{
							PriceList pl = extractPriceList(dtc, t1, t2);
							
							if(((double)pl.positive() / (double)pl.size() > 0.6) || ((double)pl.negative() / (double)pl.size() > 0.6))
							{
								if(pl.size() > 0)
								{
									int period = (timeToSecondOfDay(t2)-timeToSecondOfDay(t1))/60;
									pw.printf("%s\t%s\t", t1, t2);
									pw.printf("%d\t", period);
									pw.printf("%.5f\t", pl.sum()*10000.0);
									pw.printf("%.5f\t", pl.average()*10000.0);
									pw.printf("%.2f\t", pl.average()/(double)period*10000.0);
									pw.printf("%d\t", pl.size());
									pw.printf("%.5f\t", pl.stdev()*10000.0);
									pw.printf("%.5f\t", pl.median()*10000.0);
									pw.printf("%.5f\t", pl.range()*10000.0);
									pw.printf("%.5f\t", pl.min()*10000.0);
									pw.printf("%.5f\t", pl.max()*10000.0);
									pw.printf("%d\t", pl.positive());
									pw.printf("%.3f\t", ((double)pl.positive() / (double)pl.size()));
									pw.printf("%d\t", pl.negative());
									pw.printf("%.3f\t", ((double)pl.negative() / (double)pl.size()));
									pw.printf("%.5f\t", pl.median(.1)*10000.0);
									pw.printf("%.5f\t", pl.median(.2)*10000.0);
									pw.printf("%.5f\t", pl.median(.3)*10000.0);
									pw.printf("%.5f\t", pl.median(.4)*10000.0);
									pw.printf("%.5f\t", pl.median(.5)*10000.0);
									pw.printf("%.5f\t", pl.median(.6)*10000.0);
									pw.printf("%.5f\t", pl.median(.7)*10000.0);
									pw.printf("%.5f\t", pl.median(.8)*10000.0);
									pw.printf("%.5f\t", pl.median(.9)*10000.0);
									pw.println();
								}
							}
						}
					}
				}
				
				pw.flush();
				pw.close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static int timeToSecondOfDay(String time)
	{
		int hr = Integer.parseInt(time.substring(0, 2));
		int min = Integer.parseInt(time.substring(3, 5));
		int sec = Integer.parseInt(time.substring(6));
		return hr*3600 + min*60 + sec;
	}
	
	private PriceList extractPriceList(DeltaTableCollection dtc, String t1, String t2)
	{
		// create a price list for a time-pair
		PriceList pl = new PriceList(t1 + " to " + t2);

		// add each time-pair from the delta tables
		for(String dateKey : dtc.getTables().keySet())
		{
			DeltaTable dt = dtc.getTables().get(dateKey);
			
			double diff = dt.getDifference(t1, t2);
			pl.addPrice(dateKey, diff);
		}
		
		return pl;
	}
	
	private DeltaTableCollection loadDeltaTables(String dataSource, Date startDate, int days, String instrument) throws SQLException
	{
		DeltaTableCollection dtc = new DeltaTableCollection();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		
		for(int day = 0; day < days; day++)
		{
			cal.add(Calendar.DATE, 1);
			String date = df.format(cal.getTime());
			DeltaTable dt = loadDeltaTable(cal.getTime(), dataSource, instrument);
			dtc.add(date, dt);
			
			System.out.println("loaded table for " + date + ": " + dt.size());
		}
		
		return dtc;
	}
	
	private DeltaTable loadDeltaTable(Date date, String dataSourceId, String instrument) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		String sql = "select time(concat(hour(date_time),':',5*(minute(date_time) div 5))) time, count(*) qty, avg(buy) avg, max(buy) h," + 
		    "min(buy) l, (std(buy)*10000) stdev, round((max(buy)-min(buy))*10000) spread from tick_data where data_source_id = ? and instrument_id = ? " +
		    "and date_time >= timestamp(?) and date_time < timestamp(?) group by hour(date_time), minute(date_time) div 5";
		
		Connection con = dataSource.getConnection();
	
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, dataSourceId);
		pstmt.setString(2, instrument); 
		pstmt.setString(3, df.format(cal.getTime()));
		cal.add(Calendar.DATE, 1);
		pstmt.setString(4, df.format(cal.getTime()));
		
		DeltaTable dt = new DeltaTable();
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			String time = rs.getString("time");
			double avgPrice = rs.getDouble("avg");
			dt.addPrice(time, avgPrice);
		}
		
		pstmt.close();
		con.close();
		
		return dt;
	}
}
