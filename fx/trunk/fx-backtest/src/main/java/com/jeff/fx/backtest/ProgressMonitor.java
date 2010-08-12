package com.jeff.fx.backtest;

import org.apache.log4j.Logger;

import com.jeff.fx.datastore.DataStoreProgress;

public class ProgressMonitor {

	private static Logger log = Logger.getLogger(ProgressMonitor.class);

	private static ProgressPanel instance = new ProgressPanel();
	
	public static void update(final DataStoreProgress progress) {
		instance.setTaskHeading("Loading Data");
		instance.setMaximum(progress.getSteps());
		instance.setProgress(progress.getProgress());
		instance.setMessage(String.format("Loading data for day %d of %d (%.1f%%)", progress.getProgress(), progress.getSteps(), progress.getPercentage()));		
		instance.setVisible(true);
		log.debug("updating progress to " + progress.getProgress() + "/" + progress.getSteps());
	}

	public static void complete() {
		instance.setVisible(false);
		log.debug("updating progress to complete");
	}
}
