package com.jeff.fx.datasource.forexite;

import java.io.FileOutputStream;
import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MassDownloadScripter {
	
	// http://www.forexite.com/free_forex_quotes/2001/01/310101.zip

	private static String urlPattern = "http://www.forexite.com/free_forex_quotes/%s.zip";
	private static String localFile = "%s.zip";
	private static DateTimeFormatter df1 = DateTimeFormat.forPattern("yyyy/MM/ddMMyy");
	private static DateTimeFormatter df2 = DateTimeFormat.forPattern("ddMMyy");
	private static DateTimeFormatter df3 = DateTimeFormat.forPattern("yyyy/MM/");
	private static LocalDate start = new LocalDate(2001, 1, 3);
	private static LocalDate end = new LocalDate();
	
	public static void main(String[] args) throws IOException {
		
		FileOutputStream fos = new FileOutputStream("y:/temp/dl-script.sh");
		
		while(start.isBefore(end)) {
			String url = String.format(urlPattern, df1.print(start));
			String fn = String.format(localFile, df2.print(start));
			String dir = "www/forexite/com/free_forex_quotes/" + df3.print(start);
			String mkdir = "mkdir -p " + dir + "\n";
			String cmd = "wget -O " + dir + fn + " " + url + "\n";
			fos.write(mkdir.getBytes());
			fos.write(cmd.getBytes());
			start = start.plusDays(1);
		}
		
		fos.close();
	}
}
