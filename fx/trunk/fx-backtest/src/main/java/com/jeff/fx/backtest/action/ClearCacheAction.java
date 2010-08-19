package com.jeff.fx.backtest.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.BackTestDataManager;

@SuppressWarnings("serial")
@Component
public class ClearCacheAction extends AbstractAction {
	
	private static Logger log = Logger.getLogger(ClearCacheAction.class);

	@Autowired
	private BackTestDataManager dataManager;
	
	public ClearCacheAction() {
		putValue(SHORT_DESCRIPTION, "Clear Cache");
		putValue(LONG_DESCRIPTION, "Clear the data store cache");
		putValue(NAME, "Clear Cache");
	}

	public void actionPerformed(ActionEvent ev) {
		
		if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the entire data store?", "Confirm", JOptionPane.YES_NO_OPTION)) {
			
			SwingWorker<Exception,Exception> worker = new SwingWorker<Exception,Exception>() {
				@Override
				protected Exception doInBackground() throws Exception {
					log.debug("clearing data store");
					try {
						dataManager.clearStoreCache();
					} catch(Exception ex) {
						return ex;
					}
					return null;
				}
				
				@Override
				protected void done() {
					log.debug("cache clear complete");
					try {
						if(get() != null) {
							JOptionPane.showMessageDialog(null, get().getMessage(), get().getClass().getName(), JOptionPane.OK_OPTION);
						}
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Clearing of data store failed", ex.getClass().getName(), JOptionPane.OK_OPTION);
					}
				}
			};
			
			worker.execute();
		}
	}
}
