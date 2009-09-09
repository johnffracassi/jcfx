package com.jeff.fx.datasource.gain;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.GenericLineReader;
import com.jeff.fx.datasource.Parser;

@Component
public class GAINTickReader extends GenericLineReader<TickDataPoint> implements
		Parser<TickDataPoint> {

	private static DateTimeFormatter df1 = DateTimeFormat
			.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private static DateTimeFormatter df2 = DateTimeFormat
			.forPattern("yyyy-MM-dd HH:mm:ss");

	private static String encoding = "UTF8";
	private static FXDataSource dataSource = FXDataSource.GAIN;

	public TickDataPoint line(String str, int count) throws Exception {

		if (count == 1) {
			return null;
		}

		String[] fields = str.split(",");

		if (fields.length != 6) {
			System.out.println("Bad data line " + count + ": "
					+ str.substring(0, 200));
			return null;
		} else {
			TickDataPoint dp = new TickDataPoint();

			dp.setDataSource(getDataSource());
			dp.setInstrument(toInstrument(fields[1]));

			if (fields[2].length() == 19) {
				dp.setDate(df2.parseDateTime(fields[2]).toLocalDateTime());
			} else {
				dp.setDate(df1.parseDateTime(fields[2]).toLocalDateTime());
			}

			dp.setSell(Double.parseDouble(fields[3]));
			dp.setBuy(Double.parseDouble(fields[4]));

			return dp;
		}
	}

	public Instrument toInstrument(String str) {
		return Instrument.valueOf(str.replaceAll("/", ""));
	}

	public FXDataSource getDataSource() {
		return dataSource;
	}

	public String getCharEncoding() {
		return encoding;
	}
}
