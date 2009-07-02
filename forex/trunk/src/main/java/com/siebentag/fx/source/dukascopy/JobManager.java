package com.siebentag.fx.source.dukascopy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siebentag.fx.source.DataDownloadJob;
import com.siebentag.fx.source.Instrument;
import com.siebentag.util.DBUtil;

@Component
public class JobManager
{
	private static DataSource dataSource;

	private static String fetchSql = "select job_id,data_source_id,date,time,instrument_id,status from job where status = 'New' order by date desc,time desc limit ?";
	private static String updateSql = "update job set data_source_id=?, date=?, time=?, instrument_id=?, status=? where job_id=?";

	public static void updateJob(DataDownloadJob job) throws SQLException
	{
		Connection con = dataSource.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement(updateSql);
		prepareStatement(pstmt, job);
		pstmt.setInt(6, job.getJobId());
		
		pstmt.executeUpdate();
		
		con.close();
	}
	
	public static List<DataDownloadJob> fetchJobs(int qty) throws SQLException, ParseException
	{
		List<DataDownloadJob> jobs = new ArrayList<DataDownloadJob>(qty);

		Connection con = dataSource.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement(fetchSql);
		pstmt.setInt(1, qty);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			DataDownloadJob job = new DataDownloadJob();
			
			job.setJobId(rs.getInt("job_id"));
			job.setDataSource(rs.getString("data_source_id"));
			job.setStartDate(DBUtil.toDate(rs.getDate("date"), rs.getTime("time")));
			job.setStatus(rs.getString("status"));
			job.setInstrument(Instrument.valueOf(rs.getString("instrument_id")));
			
			jobs.add(job);
		}
		
		con.close();
		
		return jobs;
	}
	
	public static void prepareStatement(PreparedStatement pstmt, DataDownloadJob job) throws SQLException
	{
		pstmt.setString(1, job.getDataSource());
		pstmt.setDate(2, new java.sql.Date(job.getStartDate().getTime()));
		pstmt.setTime(3, new java.sql.Time(job.getStartDate().getTime()));
		pstmt.setString(4, job.getInstrument().toString());
		pstmt.setString(5, job.getStatus());
	}

	@Autowired
	public void setDataSource(DataSource ds)
	{
		JobManager.dataSource = ds;
	}
}
