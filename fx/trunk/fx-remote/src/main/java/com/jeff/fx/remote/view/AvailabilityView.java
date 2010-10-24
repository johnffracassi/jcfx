package com.jeff.fx.remote.view;

import org.jdesktop.swingx.JXPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AvailabilityView extends JXPanel {

	private JTable table;
	private AvailabilityTableModel model;

	public void setTableModel(AvailabilityTableModel model) {
		this.model = model;
		table.setModel(model);
		model.fireTableDataChanged();
	}

	public AvailabilityView() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
	
	
	
}
