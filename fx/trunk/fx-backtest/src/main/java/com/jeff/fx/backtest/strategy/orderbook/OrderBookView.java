package com.jeff.fx.backtest.strategy.orderbook;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import com.jeff.fx.gui.GUIUtil;
import com.jeff.fx.gui.LocalDateTimeCellRenderer;
import com.jeff.fx.gui.PriceCellRenderer;
import com.jeff.fx.gui.ProfitCellRenderer;

@SuppressWarnings("serial")
public class OrderBookView extends JXPanel {

	private OrderBookTableModel bookTableModel = new OrderBookTableModel();;
	private OrderBookReportTableModel reportTableModel = new OrderBookReportTableModel();
	private JXTable tblReport;
	private JXTable tblBook;
	private boolean packed = false;
	
	public OrderBookView() {
	
		setLayout(new MigLayout("", "[grow,fill][250px,fill]", "[grow,fill]"));
		
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
		JXPanel pnlTable = new JXPanel(new BorderLayout());
		pnlTable.add(sp1, BorderLayout.CENTER);
		pnlTable = GUIUtil.frame("Orders", pnlTable);
		sp1.setPreferredSize(new Dimension(640, 200));
		sp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		tblReport = new JXTable(reportTableModel);
		JScrollPane sp2 = new JScrollPane(tblReport);
		JXPanel pnlReport = new JXPanel(new BorderLayout());
		BorderLayout bl_pnlReport = (BorderLayout) pnlReport.getLayout();
		bl_pnlReport.setVgap(3);
		bl_pnlReport.setHgap(3);
		pnlReport.add(sp2, BorderLayout.CENTER);
		pnlReport = GUIUtil.frame("Report", pnlReport);
		sp2.setPreferredSize(new Dimension(240, 200));
		sp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(pnlTable);
		add(pnlReport);
	}
	
	public JXTable getTblBook() {
		return tblBook;
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
