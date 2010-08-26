package com.jeff.fx.gui;

import org.apache.log4j.Logger;

import com.jeff.fx.datastore.DataStoreProgress;

/**
 * TODO Progress monitor needs to be more generic
 */
public class ProgressMonitor {

	private static Logger log = Logger.getLogger(ProgressMonitor.class);

	private static ProgressPanel instance = new ProgressPanel();
	
	public static void update(final DataStoreProgress progress) {
		instance.setTaskHeading("Loading Data");
		instance.setMaximum(progress.getSteps());
		if(progress.getSteps() == 0) {
			instance.getProgressBar().setIndeterminate(true);
		} else {
			instance.setProgress(progress.getProgress());
		}
		instance.setMessage(progress.getMessage());		
		instance.setVisible(true);
		log.debug("updating progress to " + progress.getProgress() + "/" + progress.getSteps());
	}

	public static void complete() {
		instance.setVisible(false);
		log.debug("updating progress to complete");
	}
}
