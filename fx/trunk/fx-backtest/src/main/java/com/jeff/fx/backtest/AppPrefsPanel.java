package com.jeff.fx.backtest;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class AppPrefsPanel extends JPanel {
	
	private static final long serialVersionUID = -8344441518165657902L;
	private JTextField txtDataStore;
	private JTextField txtDownloadCache;
	private JComboBox cboLookAndFeel;

	/**
	 * Create the panel.
	 */
	public AppPrefsPanel() {
		setLayout(new MigLayout("", "[][][grow]", "[][][][]"));
		
		JLabel lblDataStoreLocation = new JLabel("Data Store");
		lblDataStoreLocation.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblDataStoreLocation, "cell 1 1,alignx left");
		
		txtDataStore = new JTextField();
		add(txtDataStore, "cell 2 1,growx");
		txtDataStore.setColumns(10);
		
		JLabel lblDownloadCache = new JLabel("Download Cache");
		lblDownloadCache.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblDownloadCache, "cell 1 2,alignx left");
		
		txtDownloadCache = new JTextField();
		add(txtDownloadCache, "cell 2 2,growx");
		txtDownloadCache.setColumns(10);
		
		JLabel lblLookAndFeel = new JLabel("Look and Feel");
		lblLookAndFeel.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblLookAndFeel, "cell 1 3,alignx left");
		
		cboLookAndFeel = new JComboBox();
		add(cboLookAndFeel, "cell 2 3,alignx left");

	}

	public JTextField getTxtDataStore() {
		return txtDataStore;
	}
	public JTextField getTxtDownloadCache() {
		return txtDownloadCache;
	}
	public JComboBox getCboLookAndFeel() {
		return cboLookAndFeel;
	}
}
