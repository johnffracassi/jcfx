package com.jeff.fx.backtest;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private JButton btnDataStore;
	private JButton btnDownloadCache;

	/**
	 * Create the panel.
	 */
	public AppPrefsPanel() {
		setLayout(new MigLayout("", "[][][grow]", "[][][][]"));
		
		JLabel lblDataStoreLocation = new JLabel("Data Store");
		lblDataStoreLocation.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblDataStoreLocation, "cell 1 1,alignx left");
		
		txtDataStore = new JTextField();
		add(txtDataStore, "flowx,cell 2 1,growx");
		txtDataStore.setColumns(10);
		
		JLabel lblDownloadCache = new JLabel("Download Cache");
		lblDownloadCache.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblDownloadCache, "cell 1 2,alignx left");
		
		txtDownloadCache = new JTextField();
		add(txtDownloadCache, "flowx,cell 2 2,growx");
		txtDownloadCache.setColumns(10);
		
		JLabel lblLookAndFeel = new JLabel("Look and Feel");
		lblLookAndFeel.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblLookAndFeel, "cell 1 3,alignx left");
		
		cboLookAndFeel = new JComboBox();
		add(cboLookAndFeel, "cell 2 3,alignx left");
		
		btnDataStore = new JButton("");
		btnDataStore.setMargin(new Insets(2, 2, 2, 2));
		btnDataStore.setIcon(new ImageIcon(AppPrefsPanel.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		add(btnDataStore, "cell 2 1");
		
		btnDownloadCache = new JButton("");
		btnDownloadCache.setMargin(new Insets(2, 2, 2, 2));
		btnDownloadCache.setIcon(new ImageIcon(AppPrefsPanel.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		add(btnDownloadCache, "cell 2 2");

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
	public JButton getBtnDataStore() {
		return btnDataStore;
	}
	public JButton getBtnDownloadCache() {
		return btnDownloadCache;
	}
}
