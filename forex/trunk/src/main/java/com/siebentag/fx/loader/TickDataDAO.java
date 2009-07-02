package com.siebentag.fx.loader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.fx.TickDataPoint;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component("tickLoader")
public class TickDataDAO
{
	private static DataSource dataSource;
	
	private static final String insertSql = "INSERT INTO tick_data(data_source_id, instrument_id, date_time, time, milliseconds, buy, sell, buy_volume, sell_volume, job_id) values (?,?,?,?,?,?,?,?,?,?)";
	private static final String selectSql = "select date_time, buy, sell, buy_volume, sell_volume from tick_data where data_source_id = ? and instrument_id = ? and date_time >= ? and date_time < ?";

	
	/**
	 * Search for tick data
	 * 
	 * @param dataSourceId
	 * @param instrument
	 * @param from
	 * @param interval
	 * @return
	 * @throws SQLException
	 */
	public static List<TickDataPoint> find(FXDataSource fxDataSource, Instrument instrument, Date from, int interval) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(from);
		
		List<TickDataPoint> ticks = new ArrayList<TickDataPoint>(5000);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try
		{
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(selectSql);
			
			pstmt.setString(1, fxDataSource.toString());
			pstmt.setString(2, instrument.toString());
			pstmt.setTimestamp(3, new Timestamp(cal.getTimeInMillis()));
			cal.add(Calendar.SECOND, interval);
			pstmt.setTimestamp(4, new Timestamp(cal.getTimeInMillis()));
			
//			System.out.println(selectSql);
//			System.out.println(fxDataSource + " / " + instrument + " / " + cal.getTime() + " / " + interval);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				try
				{
					ticks.add(read(rs, fxDataSource, instrument, from));
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		finally
		{
			try { pstmt.close(); } catch(Exception ex) {}
			try { con.close(); } catch(Exception ex) {}
		}
		
		return ticks;
	}
	
	
	/**
	 * Insert a collection of ticks
	 * 
	 * @param data
	 * @param jobId
	 * @throws SQLException
	 */
	public static void save(List<TickDataPoint> data, int jobId) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try
		{
			con = dataSource.getConnection();
		
			pstmt = con.prepareStatement(insertSql);
			
			for(TickDataPoint tick : data)
			{
				tick.setJobId(jobId);
				setParameters(pstmt, tick);
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
		}
		finally
		{
			try { pstmt.close(); } catch(Exception ex) {}
			try { con.close(); } catch(Exception ex) {}
		}
	}
	
	
	/**
	 * Build a single tick from a result set
	 * 
	 * @param rs
	 * @param dataSourceId
	 * @param instrument
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	private static TickDataPoint read(ResultSet rs, FXDataSource dataSourceId, Instrument instrument, Date date) throws SQLException 
	{
		TickDataPoint tick = new TickDataPoint();

		tick.setDataSource(dataSourceId);
		tick.setInstrument(instrument.toString());
		tick.setBuy(rs.getDouble("buy"));
		tick.setSell(rs.getDouble("sell"));
		tick.setBuyVolume(rs.getLong("buy_volume"));
		tick.setSellVolume(rs.getLong("sell_volume"));		

//		Date tickDate = DateUtil.fromMySQL(rs.getTimestamp("date_time"), rs.getInt("milliseconds"));
		Date tickDate = rs.getTimestamp("date_time");
		tick.setDate(tickDate);
		
		return tick;
	}
	
	
	/**
	 * Set the statement parameters for a single tick
	 * 
	 * @param pstmt
	 * @param data
	 * @throws SQLException
	 */
	private static void setParameters(PreparedStatement pstmt, TickDataPoint data) throws SQLException
	{
		pstmt.setString(1, data.getDataSource().toString());
		pstmt.setString(2, data.getInstrument());
		pstmt.setTimestamp(3, new Timestamp(data.getDate().getTime()));
		pstmt.setTime(4, new Time(data.getDate().getTime()));
		pstmt.setInt(5, (int)(data.getDate().getTime() % 1000));
		pstmt.setDouble(6, data.getBuy());
		pstmt.setDouble(7, data.getSell());
		pstmt.setDouble(8, data.getBuyVolume());
		pstmt.setDouble(9, data.getSellVolume());
		pstmt.setInt(10, data.getJobId());
	}
	
	
	@Autowired
	public void setDataSource(DataSource ds)
	{
		dataSource = ds;
	}
}

