package com.jeff.fx.backtest;

import javax.swing.SwingUtilities;

import com.jeff.fx.datastore.DataStoreProgress;

public class ProgressMonitor {

	private static ProgressPanel instance = new ProgressPanel();
	
	public static void update(final DataStoreProgress progress) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				instance.setTaskHeading("Loading Data");
				instance.setMaximum(progress.getSteps());
				instance.setProgress(progress.getProgress());
				instance.setMessage(String.format("Loading data for day %d of %d (%.1f%%)", progress.getProgress(), progress.getSteps(), progress.getPercentage()));		
				instance.setVisible(true);
			}
		});
	}

	public static void complete() {
		instance.setVisible(false);
	}
}
