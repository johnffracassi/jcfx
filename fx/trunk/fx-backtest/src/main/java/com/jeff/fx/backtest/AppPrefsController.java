package com.jeff.fx.backtest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDialog;
import org.springframework.stereotype.Component;

@Component
public class AppPrefsController {
	
	private static Logger log = Logger.getLogger(AppPrefsController.class);

	private AppPrefsPanel view;
	
	public AppPrefsController() {
		
		view = new AppPrefsPanel();
		
		// select download cache directory
		final String downloadCache = AppCtx.getPersistent("downloadCache.root");
		view.getTxtDownloadCache().setText(downloadCache);
		view.getBtnDownloadCache().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(downloadCache);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(view);
				File selected = fc.getSelectedFile();
				view.getTxtDownloadCache().setText(selected.getAbsolutePath());
				AppCtx.setPersistent("downloadCache.root", selected.getAbsolutePath());
			}
		});
		
		// select datastore directory
		final String dataStore = AppCtx.getPersistent("dataStore.root");
		view.getTxtDataStore().setText(dataStore);
		view.getBtnDataStore().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(dataStore);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(view);
				File selected = fc.getSelectedFile();
				view.getTxtDataStore().setText(selected.getAbsolutePath());
				AppCtx.setPersistent("dataStore.root", selected.getAbsolutePath());
			}
		});
				
		// create l&f dropdown
	    UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
	    DefaultComboBoxModel model = (DefaultComboBoxModel)view.getCboLookAndFeel().getModel();
	    model.removeAllElements();
	    for (int i=0, n=looks.length; i<n; i++) {
	    	model.addElement(new ComboModelEntry<String>(looks[i].getName(), looks[i].getClassName()));
	    }
	    
	    view.getCboLookAndFeel().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent ev) {
				try {
					UIManager.setLookAndFeel(((ComboModelEntry<String>)view.getCboLookAndFeel().getSelectedItem()).getValue());
					SwingUtilities.updateComponentTreeUI(view.getParent().getParent().getParent());
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Can't change look and feel", "Invalid PLAF", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void showDialog() {
		log.debug("showing application preferences dialog");
		SwingUtilities.updateComponentTreeUI(view);
		final GenericDialog gd = new GenericDialog(view, "Application Preferences");
		gd.setVisible(true);
	}
	
	public AppPrefsPanel getView() {
		return view;
	}
}

class ComboModelEntry<T> {
	
	private String name;
	private T value;
	
	public ComboModelEntry(String name, T value) {
		this.value = value;
		this.name = name;
	}
	
	public T getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
}