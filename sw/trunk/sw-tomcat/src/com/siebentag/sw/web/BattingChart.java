package com.siebentag.sw.web;

import java.math.BigDecimal;
import java.util.SortedMap;

import org.apache.taglibs.standard.tag.common.sql.ResultImpl;

public class BattingChart
{
	public static String chart(ResultImpl rs)
	{
		StringBuffer dsScore = new StringBuffer();
		StringBuffer dsAvg = new StringBuffer();
		StringBuffer dsPs = new StringBuffer();
		StringBuffer dsPsAvg = new StringBuffer();
		
		SortedMap[] rows = rs.getRows();
		
		int maxScore = -100;
		int minScore = 100;
		
		// find bounds
		int inns = 0;
		int cumScore = 0;
		int cumPs = 0;
		for(SortedMap row : rows)
		{
			inns++;
			int score = ((BigDecimal)row.get("score")).intValue();
			int ps = ((BigDecimal)row.get("ps_runs")).intValue();
			
			cumScore += score;
			cumPs += ps;
			
			double cumAvg = (double)cumScore / (double)inns;
			double cumPsAvg = (double)cumPs / (double)inns;
			
			if(ps > maxScore) maxScore = ps;
			if(ps < minScore) minScore = ps;
			if(score > maxScore) maxScore = score;
			if(score < minScore) minScore = score;
			
			dsScore.append(score + ",");
			dsAvg.append(String.format("%.1f", cumAvg) + ",");
			dsPs.append(ps + ",");
			dsPsAvg.append(String.format("%.1f", cumPsAvg) + ",");
		}

		int lower = (minScore - (minScore % 5) - 5);
		int upper = (maxScore - (maxScore % 5) + 5);

		String scaling = "chds=" + (lower) + "," + (upper);
		String ds1 = dsScore.toString();
		String ds2 = dsAvg.toString();
		String ds3 = dsPs.toString();
		String ds4 = dsPsAvg.toString();
		
		String data = "chd=t2:" + ds1.substring(0, ds1.length() - 1) + 
			"|" + ds3.substring(0, ds3.length() - 1) +
			"|" + ds2.substring(0, ds2.length() - 1) +
			"|" + ds4.substring(0, ds4.length() - 1);
		
		String axes = "chxt=y&chxr=0," + lower + "," + upper + ",5";
		
		return data + "&" + scaling + "&" + axes;
	}
	
	public static String chart2(ResultImpl rs)
	{
		StringBuffer dsScore = new StringBuffer();
		StringBuffer dsAvg = new StringBuffer();
		
		SortedMap[] rows = rs.getRows();
		
		int maxScore = -100;
		int minScore = 100;
		
		// find bounds
		for(SortedMap row : rows)
		{
			int score = ((BigDecimal)row.get("score")).intValue();
			double cum_avg = ((BigDecimal)row.get("cum_avg")).doubleValue();
			
			if(score > maxScore) maxScore = score;
			if(score < minScore) minScore = score;
			
			dsScore.append(score + ",");
			dsAvg.append(String.format("%.1f", cum_avg) + ",");
		}

		String scaling = "chds=" + (minScore-3) + "," + (maxScore+3);
		String ds1 = dsScore.toString();
		String ds2 = dsAvg.toString();
		String data = "chd=t1:" + ds1.substring(0, ds1.length() - 1) + "|" + ds2.substring(0, ds2.length() - 1);
		
		int lower = (minScore - (minScore % 5) - 5);
		int upper = (maxScore - (maxScore % 5) + 5);
		String axes = "chxt=y&chxr=0," + lower + "," + upper + ",5";
		
		System.out.println(data + "&" + scaling + "&" + axes);
		
		/*
		http://chart.apis.google.com/chart?
			cht=bvg&
			chm=D,0033FF,1,0,5,1&
			chbh=a&
			chd=t1:18,19,11,19,27|18,18.5,15,16.4,20.8&
			chds=-3,30&
			chs=400x175
		*/
		
		return data + "&" + scaling + "&" + axes;
	}
}
