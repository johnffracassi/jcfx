package com.jeff.fx.datastore;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.CandleWeek;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datasource.CachedDownloader;
import com.jeff.fx.datasource.forexite.ForexiteCandleReader;
import com.jeff.fx.datasource.forexite.ForexiteLocator;
import com.jeff.fx.util.ZipUtil;

@Component("candleWeekLoader")
public class CandleWeekLoader {

	@Autowired
	private CachedDownloader downloader;
	
	@Autowired
	private ForexiteLocator locator;
	
	@Autowired
	private ForexiteCandleReader reader;
	
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-datastore.xml");
		CandleWeekLoader cwl = (CandleWeekLoader)ctx.getBean("candleWeekLoader");
		CandleWeek cw = cwl.load(Instrument.AUDUSD, new LocalDate(2010, 7, 30), Period.OneMin);
		
		System.out.println(cw.getCandle(0));
		System.out.println(cw.getCandle(1));
		System.out.println(cw.getCandle(60));
		System.out.println(cw.getCandle(150));
		System.out.println(cw.getCandle(cw.getCandleCount()-1));
	}
	
	public CandleWeek load(Instrument instrument, LocalDate date, Period period) throws Exception {
		
		LocalDate startDate = getStartOfWeek(date);
		CandleWeek cw = new CandleWeek(startDate, FXDataSource.Forexite, instrument, period);
		
		for(int i=0; i<6; i++) {
			String data = new String(ZipUtil.unzipBytes(downloader.download(locator.generateUrl(instrument, startDate.plusDays(i), period))));
			String[] lines = data.split("\n");
			for(String line : lines) {
				if(line.startsWith(instrument.toString())) {
					CandleDataPoint candle = reader.line(line, 0);
					cw.setCandle(candle);
				}
			}
		}
		
		return cw;
	}
	
	public static LocalDate getStartOfWeek(LocalDate date) {
		if(date.getDayOfWeek() == DateTimeConstants.SUNDAY) {
			return date;
		} else {
			return date.minusDays(date.getDayOfWeek());
		}
	}
}
