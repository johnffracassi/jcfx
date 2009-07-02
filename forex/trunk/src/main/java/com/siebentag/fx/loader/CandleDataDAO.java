package com.siebentag.fx.loader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component
public class CandleDataDAO
{
	private static DataSource dataSource;
	
	private Connection con;
	private PreparedStatement pstmt;
	private int batchSize = 0;
	private long startTime = 0;
	private long batchStartTime = 0;
	private int inserted = 0;
	
	public void dataReadEvent(CandleStickDataPoint data)
    {
		load(data);
    }

	@Autowired
	public void setDataSource(DataSource ds)
	{
		dataSource = ds;
	}
	
	private void openConnection()
	{
		System.out.println("Opening database connection");
		
		try
		{
			con = dataSource.getConnection();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	private Connection getConnection()
	{
		if(con == null)
		{
			openConnection();
		}
		
		return con;
	}
	
	public void prepare() throws SQLException
	{
		String sql = "INSERT INTO period_data(data_source_id, instrument_id, date_time, " +
				"period, open_buy, open_sell, high_buy, high_sell, low_buy, low_sell, " +
				"close_buy, close_sell, buy_volume, sell_volume, job_id) "; 
	    sql += "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		pstmt = getConnection().prepareStatement(sql);
		
		batchStartTime = System.nanoTime();
		startTime = System.nanoTime();
	}
	
	public void complete() throws SQLException
	{
		pstmt.executeBatch();
		con.close();
	}
	
	public static List<CandleStickDataPoint> findCandles(FXDataSource fxDataSource, Instrument instrument, String period)
	{
		List<CandleStickDataPoint> candles = new LinkedList<CandleStickDataPoint>();

		double scale = (instrument.toString().contains("JPY")) ? 0.01 : 1.0;
		
		try
		{
			String sql = "select date_time, open_buy, open_sell, high_buy, high_sell, low_buy, low_sell, close_buy, close_sell " +
						 "from period_data where data_source_id = ? and instrument_id = ? and period = ? order by date_time asc";
		
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, String.valueOf(fxDataSource));
			pstmt.setString(2, String.valueOf(instrument));
			pstmt.setString(3, period);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				CandleStickDataPoint candle = new CandleStickDataPoint();
				candle.setDate(rs.getTimestamp(1));
				candle.setDataSource(fxDataSource);
				candle.setInstrument(instrument.toString());
				candle.setPeriod(period);
				candle.setBuyOpen(rs.getDouble(2) * scale);
				candle.setSellOpen(rs.getDouble(3) * scale);
				candle.setBuyHigh(rs.getDouble(4) * scale);
				candle.setSellHigh(rs.getDouble(5) * scale);
				candle.setBuyLow(rs.getDouble(6) * scale);
				candle.setSellLow(rs.getDouble(7) * scale);
				candle.setBuyClose(rs.getDouble(8) * scale);
				candle.setSellClose(rs.getDouble(9) * scale);
				candles.add(candle);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}

		return candles;
	}
	
	public static List<CandleStickDataPoint> findCandles(FXDataSource fxDataSource, Instrument instrument, String period, LocalDate startDate, LocalDate endDate)
	{
		List<CandleStickDataPoint> candles = new LinkedList<CandleStickDataPoint>();

		double scale = (instrument.toString().contains("JPY")) ? 0.01 : 1.0;
		
		try
		{
			String sql = "select date_time, open_buy, open_sell, high_buy, high_sell, low_buy, low_sell, close_buy, close_sell " +
						 "from period_data where data_source_id = ? and instrument_id = ? and period = ? and date_time >= ? and date_time < ? order by date_time asc";
		
			Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, String.valueOf(fxDataSource));
			pstmt.setString(2, String.valueOf(instrument));
			pstmt.setString(3, period);
			pstmt.setTimestamp(4, new Timestamp(startDate.toDateTimeAtStartOfDay().getMillis()));
			pstmt.setTimestamp(5, new Timestamp(endDate.toDateTimeAtStartOfDay().getMillis()));
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				CandleStickDataPoint candle = new CandleStickDataPoint();
				candle.setDate(rs.getTimestamp(1));
				candle.setDataSource(fxDataSource);
				candle.setInstrument(instrument.toString());
				candle.setPeriod(period);
				candle.setBuyOpen(rs.getDouble(2) * scale);
				candle.setSellOpen(rs.getDouble(3) * scale);
				candle.setBuyHigh(rs.getDouble(4) * scale);
				candle.setSellHigh(rs.getDouble(5) * scale);
				candle.setBuyLow(rs.getDouble(6) * scale);
				candle.setSellLow(rs.getDouble(7) * scale);
				candle.setBuyClose(rs.getDouble(8) * scale);
				candle.setSellClose(rs.getDouble(9) * scale);
				candles.add(candle);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}

		return candles;
	}
	
	public static void load(List<CandleStickDataPoint> list)
	{
		try
		{
			String sql = "INSERT INTO period_data(data_source_id, instrument_id, date_time, " +
				"period, open_buy, open_sell, high_buy, high_sell, low_buy, low_sell, " +
				"close_buy, close_sell, buy_volume, sell_volume, job_id, time, tick_count) ";
				
		    sql += "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
		    Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			long batchStartTime = System.nanoTime();
			long startTime = System.nanoTime();
		
			int batchSize = 0;
			int inserted = 0;
			for(CandleStickDataPoint data : list)
			{
				try
				{
					int i=1;
					pstmt.setString(i++, data.getDataSource().toString());
					pstmt.setString(i++, data.getInstrument());
					pstmt.setTimestamp(i++, new Timestamp(data.getDate().getTime()));
					pstmt.setString(i++, data.getPeriod());
					pstmt.setDouble(i++, data.getBuyOpen());
					pstmt.setDouble(i++, data.getSellOpen());
					pstmt.setDouble(i++, data.getBuyHigh());
					pstmt.setDouble(i++, data.getSellHigh());
					pstmt.setDouble(i++, data.getBuyLow());
					pstmt.setDouble(i++, data.getSellLow());
					pstmt.setDouble(i++, data.getBuyClose());
					pstmt.setDouble(i++, data.getSellClose());
					pstmt.setDouble(i++, data.getBuyVolume());
					pstmt.setDouble(i++, data.getSellVolume());
					pstmt.setInt(i++, data.getJobId());
					pstmt.setTime(i++, new Time(data.getDate().getTime()));
					pstmt.setInt(i++, data.getTickCount());
					pstmt.addBatch();
					batchSize ++;
					inserted ++;
					
					if(batchSize >= 1000)
					{
						double batchTime = ((System.nanoTime() - batchStartTime) / 1000000.0);
						double time = ((System.nanoTime() - startTime) / 1000000000.0);
						pstmt.executeBatch();
						System.out.printf("Executed batch - %d / %.1fms | %d / %.3fs\n", batchSize, batchTime, inserted, time);
						batchSize = 0;
						batchStartTime = System.nanoTime();
					}
				}
				catch(Exception ex)
				{
					System.out.println("Error with: " + data);
				}
			}
			
			pstmt.executeBatch();
			con.close();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public void load(CandleStickDataPoint data)
	{
		try
		{
			int i=1;
			pstmt.setString(i++, data.getDataSource().toString());
			pstmt.setString(i++, data.getInstrument());
			pstmt.setDate(i++, new java.sql.Date(data.getDate().getTime()));
			pstmt.setTime(i++, new java.sql.Time(data.getDate().getTime()));
			pstmt.setString(i++, data.getPeriod());
			pstmt.setDouble(i++, data.getBuyOpen());
			pstmt.setDouble(i++, data.getSellOpen());
			pstmt.setDouble(i++, data.getBuyHigh());
			pstmt.setDouble(i++, data.getSellHigh());
			pstmt.setDouble(i++, data.getBuyLow());
			pstmt.setDouble(i++, data.getSellLow());
			pstmt.setDouble(i++, data.getBuyClose());
			pstmt.setDouble(i++, data.getSellClose());
			pstmt.setDouble(i++, data.getBuyVolume());
			pstmt.setDouble(i++, data.getSellVolume());
			pstmt.addBatch();
			batchSize ++;
			inserted ++;
			
			if(batchSize >= 1000)
			{
				double batchTime = ((System.nanoTime() - batchStartTime) / 1000000.0);
				double time = ((System.nanoTime() - startTime) / 1000000000.0);
				pstmt.executeBatch();
				System.out.printf("Executed batch - %d / %.1fms | %d / %.3fs\n", batchSize, batchTime, inserted, time);
				batchSize = 0;
				batchStartTime = System.nanoTime();
			}
		}
		catch(SQLException ex)
		{
//			ex.printStackTrace();
		}
	}
}








