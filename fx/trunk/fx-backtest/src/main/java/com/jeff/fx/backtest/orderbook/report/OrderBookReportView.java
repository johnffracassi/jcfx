package com.jeff.fx.backtest.orderbook.report;

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
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class OrderBookReportView extends JXPanel {

	private OrderBookTableModel bookTableModel = new OrderBookTableModel();;
	private OrderBookReportTableModel reportTableModel = new OrderBookReportTableModel();
	private JXTable tblReport;
	private JXTable tblBook;
	private boolean packed = false;
	private JXPanel pnlReport_1;
	private JXPanel pnlTable_1;
	private JButton btnExport;
	
	public OrderBookReportView() {
	
		setLayout(new MigLayout("", "[grow,fill][250px,fill]", "[grow,fill][]"));
		
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
		JXPanel pnlTable;
		pnlTable_1 = new JXPanel(new BorderLayout());
		pnlTable_1.add(sp1, BorderLayout.CENTER);
		pnlTable = GUIUtil.frame("Orders", pnlTable_1);
		sp1.setPreferredSize(new Dimension(640, 200));
		sp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		pnlTable_1.add(panel, BorderLayout.SOUTH);
		btnExport = new JButton("Export");
		btnExport.setIcon(new ImageIcon(OrderBookReportView.class.getResource("/images/report_disk.png")));
		panel.add(btnExport);
		
		tblReport = new JXTable(reportTableModel);
		JScrollPane sp2 = new JScrollPane(tblReport);
		JXPanel pnlReport;
		pnlReport_1 = new JXPanel(new BorderLayout());
		BorderLayout bl_pnlReport = (BorderLayout) pnlReport_1.getLayout();
		bl_pnlReport.setVgap(3);
		bl_pnlReport.setHgap(3);
		pnlReport_1.add(sp2, BorderLayout.CENTER);
		pnlReport = GUIUtil.frame("Report", pnlReport_1);
		sp2.setPreferredSize(new Dimension(240, 200));
		sp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		add(pnlTable, "cell 0 0,grow");
		add(pnlReport, "cell 1 0");
	}
	
	public JXTable getTblBook() {
		return tblBook;
	}
	
	public void update(OrderBookReportModel model) {
		
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
	public JButton getBtnExport() {
		return btnExport;
	}
}
