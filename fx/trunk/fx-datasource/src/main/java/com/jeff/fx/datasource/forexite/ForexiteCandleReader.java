package com.jeff.fx.datasource.forexite;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datasource.GenericLineReader;
import com.jeff.fx.datasource.Parser;

@Component
public class ForexiteCandleReader extends GenericLineReader<CandleDataPoint> implements Parser<CandleDataPoint> {

	/* File extract:
	
	<TICKER>,<DTYYYYMMDD>,<TIME>,<OPEN>,<HIGH>,<LOW>,<CLOSE>
	EURUSD,20100426,000000,1.3389,1.3390,1.3389,1.3390
	EURUSD,20100426,000100,1.3345,1.3345,1.3343,1.3344
	EURUSD,20100426,000200,1.3345,1.3345,1.3345,1.3345
	
	
	if DST = True, then NY Price [t] = Forexite Price [t - 5 hours];
	if DST = False, then NY Price [t] = Forexite Price [t - 6 hours].
	
	*/
	
	private static FXDataSource dataSource = FXDataSource.Forexite;
	private static DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZone(DateTimeZone.forTimeZone(dataSource.getCalendar().getTimeZone()));
	private static double SPREAD = 4;
	
	public CandleDataPoint line(String str, int count) {

		// first line of the file is a header
		if (count == 1) {
			return null;
		}

		String[] fields = str.split(",");

		if (fields.length != 7) {
			System.out.println("Bad data line " + count + ": " + str.substring(0, 200));
			return null;
		} else {
			CandleDataPoint dp = new CandleDataPoint();

			Instrument ins = Instrument.valueOf(fields[0]);
			
			dp.setDataSource(getDataSource());
			dp.setInstrument(ins);
			dp.setPeriod(Period.OneMin);
			
			DateTime dateTime = dateTimeFormat.parseDateTime(fields[1] + fields[2]);
			dp.setDateTime(new LocalDateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), dateTime.getSecondOfMinute()).minusMinutes(61));

			dp.setSellOpen(Double.parseDouble(fields[3]));
			dp.setSellHigh(Double.parseDouble(fields[4]));
			dp.setSellLow(Double.parseDouble(fields[5]));
			dp.setSellClose(Double.parseDouble(fields[6]));
			dp.setBuyOpen(dp.getSellOpen() + SPREAD * ins.getPipValue());
			dp.setBuyHigh(dp.getSellHigh() + SPREAD * ins.getPipValue());
			dp.setBuyLow(dp.getSellLow() + SPREAD * ins.getPipValue());
			dp.setBuyClose(dp.getSellClose() + SPREAD * ins.getPipValue());
			
			return dp;
		}
	}

	public FXDataSource getDataSource() {
		return dataSource;
	}
}
