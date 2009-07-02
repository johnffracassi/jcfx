package com.siebentag.fx.source.dukascopy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.source.DataDownloadJob;
import com.siebentag.fx.source.Instrument;

@Component
public class JobCreator
{
	@Autowired
	private DataSource dataSource;
	
	private String sql = "INSERT INTO job(data_source_id, date, time, instrument_id, status) VALUES(?,?,?,?,?)";
	
	public static void main(String[] args)
    {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    JobCreator app = (JobCreator)ctx.getBean("jobCreator");
	    app.run();
    }
	
	public void run()
	{
		try
		{ 
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			int records = 0;
			int batchSize = 0;
			
		    Calendar cal = Calendar.getInstance();
//		    cal.set(Calendar.YEAR, 2008);
//		    cal.set(Calendar.MONTH, Calendar.DECEMBER);
//		    cal.set(Calendar.DAY_OF_MONTH, 31);
//		    cal.set(Calendar.HOUR, 10);
		    cal.set(Calendar.MINUTE, 0);
		    cal.set(Calendar.SECOND, 0);
		    cal.set(Calendar.MILLISECOND, 0);
		    
		    while(cal.get(Calendar.YEAR) >= 2008)
		    {
		    	if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
		    	{
			    	for(Instrument instrument : Instrument.values())
			    	{
//		    		Instrument instrument = Instrument.GBPUSD;
		    		
					    DataDownloadJob job = new DataDownloadJob();
					    job.setDataSource("Dukascopy");
					    job.setStatus("New");
					    job.setStartDate(cal.getTime());
					    job.setInstrument(instrument);
					    
					    JobManager.prepareStatement(pstmt, job);
					    try
					    {
					    	pstmt.executeUpdate();
						    records ++;
					    }
					    catch(Exception ex)
					    {
					    	ex.printStackTrace();
					    }
//					    pstmt.addBatch();
//					    batchSize ++;
			    	}
		    	}
		    	
		    	cal.add(Calendar.HOUR, -1);
		    	
//		    	if(batchSize > 1000)
//		    	{
//		    		pstmt.executeBatch();
//		    		System.out.println("Executing batch (" + batchSize + " / " + records + " inserted)");
//		    		batchSize = 0;
//		    	}
		    }
		    
//		    pstmt.executeBatch();		    
    		System.out.println("Executing batch (" + batchSize + " / " + records + " inserted)");
		    con.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
