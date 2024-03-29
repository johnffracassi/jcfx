package com.jeff.fx.datastore.file;

import java.io.File;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;

public class FileLocator {
	
	private static Logger log = Logger.getLogger(FileLocator.class);

	private String dataRoot;
	private String filenamePattern;
	private String extension;

	public FileLocator() {
	}

	public File locate(FXDataSource dataSource, Instrument instrument, LocalDate date, Period period) {
		
		String pathStr = String.format(filenamePattern, dataSource, instrument, date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
		String filename = String.format("%s.%s", period.key, getExtension());
		File store = new File(dataRoot + pathStr, filename);

		log.debug("[" + dataSource + "/" + instrument + "/" + period + "/" + date + "] => " + store);

		return store;
	}

	public File locate(FXDataRequest request) {
		return locate(request.getDataSource(), request.getInstrument(), request.getDate(), request.getPeriod());
	}

	public String getDataRoot() {
		return dataRoot;
	}

	public void setDataRoot(String dataRoot) {
		this.dataRoot = dataRoot;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFilenamePattern() {
		return filenamePattern;
	}

	public void setFilenamePattern(String filenamePattern) {
		this.filenamePattern = filenamePattern;
	}
}
