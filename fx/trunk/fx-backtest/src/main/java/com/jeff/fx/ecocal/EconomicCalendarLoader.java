package com.jeff.fx.ecocal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.joda.time.LocalDate;

public class EconomicCalendarLoader {

	public static String load(LocalDate startOfWeek) throws IOException {

		long sow = startOfWeek.toDateTimeAtStartOfDay().toDate().getTime() / 1000;
		URL url = new URL("http://www.forexfactory.com/calendar.php?c=2&week=" + sow + "&do=displayweek&month=8&year=2010");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoInput(true);
		connection.setDoOutput(false);
		connection.addRequestProperty("Host", "www.forexfactory.com");
		connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");
		connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		connection.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
		connection.addRequestProperty("Accept-Encoding", "gzip,deflate");
		connection.addRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		connection.addRequestProperty("Keep-Alive", "115");
		connection.addRequestProperty("Connection", "keep-alive");
		connection.addRequestProperty("Cookie","fflastvisit=1282485590; fflastactivity=0; __utma=113005075.1102583263.1282485598.1282559583.1282564245.5; __utmz=113005075.1282485598.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=forex%20economic%20calendar%20historical; used_tips=options%2Cscrolling; ffcurfilter=AUD%2CCAD%2CCHF%2CCNY%2CEUR%2CGBP%2CJPY%2CNZD%2CUSD; ffimpfilter=high%2Cmed%2Clow%2Cnonec; ffsetfilter=0; market_bq=%5B%7B%22pair%22%3A%22AUDUSD%22%2C%22platforms%22%3A%5B%22%27FCM%27%22%2C%22%27FXS%27%22%2C%22%27IBF%27%22%2C%22%27IGI%27%22%5D%7D%5D; mlive_sessions=1; used_market_tips=scrolling; ffcalendar=fce4c651c4b489836fcb1c5b68ae80d1eae72005a-3-%7Bs-7-.calyear._i-2010_s-8-.calmonth._i-8_s-8-.calview2._s-11-.displayweek._%7D; __utmc=113005075; ffsessionhash=e3c4226ff7fe2f08f579bec10517303e; __utmb=113005075.2.10.1282564245");

//		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//		out.write("Referer: http://www.forexfactory.com/charts/main.swf?v=1.1.1\n".getBytes());
//		out.write("Content-type: application/x-www-form-urlencoded\n".getBytes());
//		out.write("Content-length: 23\n".getBytes());
//		out.write("eventid=28472&chart=eco".getBytes());
//		out.flush();
//		out.close();
		
		InputStream in = connection.getInputStream();

		StringBuffer buf = new StringBuffer();
		int ch;
		while (((ch = in.read()) != -1))
			buf.append((char) ch);

		in.close();

		return buf.toString();
	}
}
