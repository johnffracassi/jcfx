package com.jeff.fx.ecocal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.joda.time.LocalDate;

public class EconomicCalendarParser {
	
	public static void main(String[] args) throws IOException {
		
		EconomicCalendarParser ecp = new EconomicCalendarParser();
		LocalDate date = new LocalDate(2010, 8, 23);

		while(true) {
			String data = EconomicCalendarLoader.load(date);
			String parsed = ecp.parse(data);
	
			FileOutputStream out = new FileOutputStream(new File(String.format("../cache/news/news" + date.toString("yyyyMMdd") + ".tsv")));
			out.write(parsed.getBytes());
			out.flush();
			out.close();
			
			System.out.println("completed " + date);

			date = date.minusDays(7);
		}
	}
	
	private String parse(String data) throws IOException {
		
		StringBuffer buf = new StringBuffer();
		
		int startIdx = data.indexOf("<table id=\"weekdays\"");
		int endIdx = data.indexOf("</table>", startIdx);
		
		String table = data.substring(startIdx, endIdx);
		
		String rows[] = splitRows(table);

		String lastDate = "";
		
		for(int r=1; r<rows.length; r++) {
			String cells[] = splitCells(rows[r]);
			int idxOffset = 0;
			if(cells.length == 10) 
				idxOffset = -1;
			
			for(int i=1; i<=9+idxOffset; i++) {
				if(i==1) {
					if(idxOffset==0) {
						lastDate = getCellText(cells[i]);
						buf.append(lastDate + "\t");
					} else { 
						buf.append(lastDate + "\t" + getCellText(cells[i]) + "\t");
					}
				} else if(i==4+idxOffset) {
					buf.append(findImpact(cells[i]) + "\t");
				} else if(i==6+idxOffset) {
					// skip
				} else {
					String txt = getCellText(cells[i]);
					buf.append(txt + "\t");
				}
			}
			buf.append("\n");
		}
		
		return buf.toString();
	}
	
	private String getCellText(String cell) {
		StringBuffer buf = new StringBuffer();
		String splits[] = splitTags("<" + cell);
		for(String str : splits) {
			String cellText = cleanCell(str);
			if(cellText.length() > 0) {
				buf.append(cellText);
			}
		}
		return buf.toString();
	}
	
	private String[] splitRows(String data) {
		return data.split("<tr id=\"detail_row_seek");
	}
	
	private String[] splitCells(String data) {
		return data.split("<td");
	}
	
	private String[] splitTags(String data) {
		return data.split("<");
	}
	
	private String cleanCell(String data) {
		return data.replaceAll(".*>", "").trim();
	}
	
	private String findImpact(String data) {
		try {
			data = data.substring(data.indexOf("class=\"cal_imp_"));
			return data.substring("class=\"cal_imp_".length(), data.indexOf("\"", "class=\"cal_imp_".length()));
		} catch(Exception ex) {
			return "ERROR";
		}
	}
	
	private String stripTags(String data, String ... tags) {
		for(String tag : tags) {
			data = data.replaceAll("<" + tag + ">", "");
			data = data.replaceAll("<" + tag + ".*>", "");
			data = data.replaceAll("</" + tag + ">", "");
		}
		return data;
	}
	
	private String removeNewLines(String data) {
		return data.replaceAll("\\s{2,}?", "\n");
	}
	
	private String removeTabs(String data) {
		return data.replaceAll("\\t*", "");
	}
	
	private String stripComments(String data) {
		return data.replaceAll("<!--.*-->", "COMMENT");
	}
	
	//view-source:http://www.forexfactory.com/calendar.php?c=2&week=1282405200&do=displayweek&month=8&year=2009
		
}
