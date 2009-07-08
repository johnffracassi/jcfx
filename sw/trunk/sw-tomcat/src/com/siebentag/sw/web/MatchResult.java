package com.siebentag.sw.web;

import java.util.SortedMap;

import org.apache.taglibs.standard.tag.common.sql.ResultImpl;

public class MatchResult
{
	public static String batting(ResultImpl rs)
	{
		int[][] totals = new int[8][5];
		int[] bf = new int[8];
		String[][] table = new String[8][27];
		String[][] styles = new String[8][27];
		
		String[] batsmen = new String[8];
		
		SortedMap[] rows = rs.getRows(); 

		// initialise table
		for(int i=0; i<table.length; i++)
		{
			for(int j=0; j<table[i].length; j++)
			{
				table[i][j] = "&nbsp;";
				styles[i][j] = "ball";
			}
		}
		
		// build the data table
		for(SortedMap row : rows)
		{
			Integer inns = (Integer)row.get("inns_ordinal");
			String batsman = (String)row.get("striker_id");
			Integer score = (Integer)row.get("runs");
			Integer ordinal = (Integer)row.get("ball_ordinal");
			Integer nb = (Integer)row.get("noball");
			Integer wide = (Integer)row.get("wide");
			String wicket = (String)row.get("wicket_type");
			String boundary = (String)row.get("boundary");

			int innsIdx = inns - 1;
			int ballIdx = ordinal - 1;
			batsmen[innsIdx] = batsman;
			
			if(wicket != null)
			{
				table[innsIdx][ballIdx] += wicket.charAt(0);
				styles[innsIdx][ballIdx] +=  " wick";
			}
			else
			{
				if(nb != null && nb > 0)
				{
					table[innsIdx][ballIdx] = "NB" + (score>2?score-2:"");
					styles[innsIdx][ballIdx] +=  " nb";
				}
				else if(wide != null && wide > 0)
				{
					table[innsIdx][ballIdx] = "W" + (score>2?score-2:"");
					styles[innsIdx][ballIdx] +=  " wd";
				}
				else
				{
					table[innsIdx][ballIdx] = String.valueOf(score);
				}

				if("SIX".equals(boundary)) styles[innsIdx][ballIdx] += " six";
				if("FOUR".equals(boundary)) styles[innsIdx][ballIdx] += " four";
			}
			
			totals[innsIdx][overOrdinal(ordinal)-1] += score;
			totals[innsIdx][4] += score;
			bf[innsIdx] ++;
		}
		
		// assemble the table
		StringBuffer buf = new StringBuffer();
		for(int row = 0; row < 8; row++)
		{
			buf.append("<tr>\n");
			buf.append("<td class=\"indName\">").append(SWFunctions.name(batsmen[row])).append("</td>\n");

			for(int over=0; over<4; over++)
			{
				int limit = (over*6+(over<3?6:9));
				for(int col=over*6; col<limit; col++)
				{
					String style = styles[row][col];
					
					if(col == limit-1)
					{
						style += " over-break";
					}
					
					buf.append("<td class=\"").append(style).append("\">");
					buf.append(table[row][col]);
					buf.append("</td>\n");
				}
	
				buf.append("<td class=\"indOver\">").append(totals[row][over]).append("</td>\n");
				
				if(row % 2 == 0)
				{
					buf.append("<td class=\"psOver\" rowspan=\"2\">").append(totals[row][over]+totals[row+1][over]).append("</td>\n");
				}
			}
			
			buf.append("<td class=\"indTotal\">").append(totals[row][4]).append(" (" + bf[row] + ")").append("</td>\n");

			if(row % 2 == 0)
				buf.append("<td class=\"psTotal\" rowspan=\"2\">").append(totals[row][4]+totals[row+1][4]).append("</td>\n");
			
			buf.append("</tr>\n");
		}

		return buf.toString();
	}
	
	public static int overOrdinal(int ball)
	{
		if(ball <= 6) return 1;
		if(ball <= 12) return 2;
		if(ball <= 18) return 3;
		else return 4;
	}
}
