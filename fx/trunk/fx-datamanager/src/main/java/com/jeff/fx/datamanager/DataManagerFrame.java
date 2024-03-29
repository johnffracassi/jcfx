package com.jeff.fx.datamanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.joda.time.LocalDate;

import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;

public class DataManagerFrame extends JFrame {

	private JComboBox cboDataSource = new JComboBox(FXDataSource.values());
	private JComboBox cboCurrencyPair = new JComboBox(Instrument.values());
	
	public DataManagerFrame() {
		setLayout(new BorderLayout());
		
		JPanel pnlControls = new JPanel(new FlowLayout());
		pnlControls.add(cboDataSource);
		pnlControls.add(cboCurrencyPair);
		
		final DataTableModel model = new DataTableModel();
		final JTable table = new JTable(model);
		final JScrollPane pnlTable = new JScrollPane(table);
		
		JButton btnLoad = new JButton("Load");
		pnlControls.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				DataLoadAction dla = new DataLoadAction();
				LocalDate date = model.getDateForRow(table.getSelectedRow());
				dla.perform((FXDataSource)cboDataSource.getSelectedItem(), (Instrument)cboCurrencyPair.getSelectedItem(), date);
			}
		});
		
		// Menu setup
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new ExitAction());		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new AboutAction());
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		
		add(pnlControls, BorderLayout.NORTH);
		add(pnlTable, BorderLayout.CENTER);
	}
}