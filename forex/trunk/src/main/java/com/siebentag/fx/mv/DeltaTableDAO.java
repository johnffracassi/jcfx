package com.siebentag.fx.mv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component
public class DeltaTableDAO
{
	@Autowired
	private DataSource dataSource;
	
//	public TickAggregationDeltaList getDeltaTable2(Date startDate, Date endDate, FXDataSource dataSource, Instrument instrument, Time startTime, Time endTime, long windowSize) throws SQLException
//	{
//		// fetch data for the start and end times
//		Map<Date,TickAggregation> start = getTickAggregationsFromPeriods(startDate, endDate, dataSource.toString(), instrument.toString(), startTime, windowSize);
//		Map<Date,TickAggregation> end   = getTickAggregationsFromPeriods(startDate, endDate, dataSource.toString(), instrument.toString(), endTime, windowSize);
//		
//		// merge aggregation keys
//		Set<Date> keySet = new HashSet<Date>();
//		keySet.addAll(start.keySet());
//		keySet.addAll(end.keySet());
//		
//		// merge start and end aggregations
//		TickAggregationDeltaList deltaList = new TickAggregationDeltaList();
//		for(Date date : keySet)
//		{
//			if(start.containsKey(date) && end.containsKey(date))
//			{
//				TickAggregationDelta delta = new TickAggregationDelta(date, start.get(date), end.get(date));
//				deltaList.add(delta);
//			}
//		}
//		
//		return deltaList;
//	}
	
	public TickAggregationDeltaList getDeltaTable(Date startDate, Date endDate, FXDataSource dataSource, Instrument instrument, Time startTime, Time endTime) throws SQLException
	{
		TickAggregationDeltaList deltaList = getTickAggregationsFromProcedure(startDate, endDate, dataSource.toString(), instrument.toString(), startTime, endTime);
		return deltaList;
	}
	
	private TickAggregationDeltaList getTickAggregationsFromProcedure(Date startDate, Date endDate, String dataSourceId, String instrument, Time timeOpen, Time timeClose) throws SQLException
	{
		TickAggregationDeltaList rows = new TickAggregationDeltaList();

		String sql = "call ohlc_tick(?, ?, ?, ?)";
		
		try
		{
			Connection con = dataSource.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dataSourceId);
			pstmt.setString(2, instrument);
			pstmt.setTime(3, timeOpen);
			pstmt.setTime(4, timeClose);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				TickAggregationRow row = new TickAggregationRow();
				
				row.setStartDate(rs.getDate("start_time"));
				row.setEndDate(rs.getDate("end_time"));
				
				row.setOpenBuy(rs.getDouble("open_buy"));
				row.setHighBuy(rs.getDouble("high_buy"));
				row.setLowBuy(rs.getDouble("low_buy"));
				row.setCloseBuy(rs.getDouble("close_buy"));

				row.setOpenSell(rs.getDouble("open_sell"));
				row.setHighSell(rs.getDouble("high_sell"));
				row.setLowSell(rs.getDouble("low_sell"));
				row.setCloseSell(rs.getDouble("close_sell"));

				rows.add(row);
			}
			
			con.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rows;
	}

//	
//	private Map<Date,TickAggregation> getTickAggregationsFromPeriods(Date startDate, Date endDate, String dataSourceId, String instrument, Time time, long windowSize) throws SQLException
//	{
//		// create an empty lookup table
//		Map<Date,TickAggregation> rows = new HashMap<Date, TickAggregation>();
//		
//		// work out the window's closing time
//		Time timeOpen = time;
//		Time timeClose = new Time(time.getTime() + windowSize);
//
//		// set bounds for date range (if none already specified)
//		if(startDate == null)
//			startDate = new Date(0);
//		
//		if(endDate == null)
//			endDate = new Date(150, 1, 1, 0, 0, 0);
//		
//		// build the sql
//		String sql =
//			"select min(date_time) start_time, max(date_time) end_time, instrument_id, open_buy open_buy, max(high_sell) high_sell, min(low_sell) low_sell, " +
//			"(select open_sell from period_data where data_source_id = ? and instrument_id = ? and period = ? and date_time = max(a.date_time)) close_sell, " +
//			"open_sell open_sell, max(high_buy) high_buy, min(low_buy) low_buy, " +
//			"(select open_buy from period_data where data_source_id = ? and instrument_id = ? and period = ? and date_time = max(a.date_time)) close_buy " +
//			"from period_data a " +
//			"where data_source_id = ? and instrument_id = ? and period = ? " +
//			"and (time >= ? and time <= ?) " +
//			"group by date(date_time)";
//
//
//		
//		try
//		{
//			Connection con = dataSource.getConnection();
//			
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, dataSourceId);
//			pstmt.setString(2, instrument);
//			pstmt.setInt(3, 60);
//			pstmt.setString(4, dataSourceId);
//			pstmt.setString(5, instrument);
//			pstmt.setInt(6, 60);
//			pstmt.setString(7, dataSourceId);
//			pstmt.setString(8, instrument);
//			pstmt.setInt(9, 60);
//			pstmt.setTime(10, timeOpen);
//			pstmt.setTime(11, timeClose);
//			
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next())
//			{
//				TickAggregation row = new TickAggregation();
//				row.setDate(new Date(rs.getDate("start_time").getTime()));
//				row.setBuy(new OHLC(rs.getDouble("open_buy"), rs.getDouble("high_buy"), rs.getDouble("low_buy"), rs.getDouble("close_buy"), 0.0));
//				row.setSell(new OHLC(rs.getDouble("open_sell"), rs.getDouble("high_sell"), rs.getDouble("low_sell"), rs.getDouble("close_sell"), 0.0));
//
//				rows.put(row.getDate(), row);
//			}
//			
//			con.close();
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		
//		return rows;
//	}
//
//	
//	private Map<Date,TickAggregation> getTickAggregationsFromTicks(Date startDate, Date endDate, String dataSourceId, String instrument, Time time, long windowSize) throws SQLException
//	{
//		// create an empty lookup table
//		Map<Date,TickAggregation> rows = new HashMap<Date, TickAggregation>();
//		
//		// work out the window's closing time
//		Time timeOpen = time;
//		Time timeClose = new Time(time.getTime() + windowSize);
//
//		// set bounds for date range (if none already specified)
//		if(startDate == null)
//			startDate = new Date(0);
//		
//		if(endDate == null)
//			endDate = new Date(150, 1, 1, 0, 0, 0);
//		
//		// build the sql
//		String sql = 
//			"select date(date_time) date, dayofweek(date_time) day, buy open_buy, sell open_sell, min(buy) low_buy, min(sell) low_sell, avg(buy) avg_buy, avg(sell) avg_sell, max(buy) high_buy, max(sell) high_sell, (max(buy)-min(buy))*10000 `range`, count(*) samples, avg(buy_volume) buy_volume, avg(sell_volume) sell_volume " + 
//			"from tick_data where data_source_id = ? and instrument_id = ? " +
//			"and time >= ? " +
//			"and time < ? " +
//			"and date_time >= ? " +
//			"and date_time <  ? " +
//			"group by date(date_time) " +
//			"order by date asc";
//		
//		try
//		{
//			Connection con = dataSource.getConnection();
//			
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, dataSourceId);
//			pstmt.setString(2, instrument);
//			pstmt.setTime(3, timeOpen);
//			pstmt.setTime(4, timeClose);
//			pstmt.setTimestamp(5, new Timestamp(startDate.getTime()));
//			pstmt.setTimestamp(6, new Timestamp(endDate.getTime()));
//			
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next())
//			{
//				TickAggregation row = new TickAggregation();
//				row.setDate(new Date(rs.getDate("date").getTime()));
//				row.setBuy(new OHLC(rs.getDouble("open_buy"), rs.getDouble("high_buy"), rs.getDouble("low_buy"), 0.0, rs.getDouble("buy_volume")));
//				row.setSell(new OHLC(rs.getDouble("open_sell"), rs.getDouble("high_sell"), rs.getDouble("low_sell"), 0.0, rs.getDouble("sell_volume")));
//
//				rows.put(row.getDate(), row);
//			}
//			
//			con.close();
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		
//		return rows;
//	}

}
