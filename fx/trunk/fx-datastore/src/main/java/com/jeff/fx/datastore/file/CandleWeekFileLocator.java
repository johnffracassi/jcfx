package com.jeff.fx.datastore.file;

import java.io.File;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.util.DateUtil;

public class CandleWeekFileLocator extends FileLocator {
	
	private static Logger log = Logger.getLogger(CandleWeekFileLocator.class);

	public CandleWeekFileLocator() {
	}

	public File locate(FXDataSource dataSource, Instrument instrument, LocalDate date, Period period) {

		date = DateUtil.getStartOfWeek(date);
		
		String pathStr = String.format(getFilenamePattern(), dataSource, instrument, date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
		String filename = String.format("%s.%s", period.key, getExtension());
		File store = new File(getDataRoot() + pathStr, filename);

		log.debug("[" + dataSource + "/" + instrument + "/" + period + "/" + date + "] => " + store);

		return store;
	}
}
