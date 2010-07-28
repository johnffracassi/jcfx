package com.jeff.fx.backtest.strategy.time;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class OrderBookView extends JXPanel {

	private OrderBookTableModel bookTableModel = new OrderBookTableModel();;
	private OrderBookReportTableModel reportTableModel = new OrderBookReportTableModel();
	private JXTable tblReport;
	private JXTable tblBook;
	private boolean packed = false;
	
	public OrderBookView() {
	
		setLayout(new BorderLayout());

		tblBook = new JXTable(bookTableModel);
		tblBook.setColumnControlVisible(true);
		tblBook.getColumnModel().getColumn(1).setCellRenderer(new LocalDateTimeCellRenderer());
		tblBook.getColumnModel().getColumn(3).setCellRenderer(new PriceCellRenderer(2));
		tblBook.getColumnModel().getColumn(5).setCellRenderer(new PriceCellRenderer(4));
		tblBook.getColumnModel().getColumn(6).setCellRenderer(new PriceCellRenderer(4));
		tblBook.getColumnModel().getColumn(7).setCellRenderer(new PriceCellRenderer(4));
		tblBook.getColumnModel().getColumn(8).setCellRenderer(new LocalDateTimeCellRenderer());
		tblBook.getColumnModel().getColumn(9).setCellRenderer(new PriceCellRenderer(4));
		tblBook.getColumnModel().getColumn(10).setCellRenderer(new ProfitCellRenderer(2));
		tblBook.getColumnModel().getColumn(11).setCellRenderer(new ProfitCellRenderer(0));
		JScrollPane sp1 = new JScrollPane(tblBook);
		sp1.setPreferredSize(new Dimension(640, 200));
		sp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		tblReport = new JXTable(reportTableModel);
		JScrollPane sp2 = new JScrollPane(tblReport);
		sp2.setPreferredSize(new Dimension(240, 200));
		sp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(sp1, BorderLayout.CENTER);
		add(sp2, BorderLayout.EAST);
	}
	
	public void update(OrderBookModel model) {
		
		bookTableModel.update(model.getOrderBook());
		bookTableModel.fireTableDataChanged();
		
		reportTableModel.update(model.getOrderBook());
		reportTableModel.fireTableDataChanged();
		
		if(!packed && bookTableModel.getRowCount() > 0) {
			tblBook.packAll();
			tblReport.packAll();
			packed = true;
		}
	}	
}
