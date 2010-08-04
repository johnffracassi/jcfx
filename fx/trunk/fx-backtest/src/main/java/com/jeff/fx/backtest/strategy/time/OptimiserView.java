package com.jeff.fx.backtest.strategy.time;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTable;

import com.jeff.fx.backtest.GUIUtil;

@SuppressWarnings("serial")
public class OptimiserView extends JPanel {

	private OptimiserParameterTableModel paramModel;
	private JXTable tblParams;
	private OptimiserReportTableModel reportModel;
	private JXTable tblReport;
	private JXTable tblResults;
	private JButton btnRun;
	private JLabel lblPermutations;
	private JLabel lblCompleted;
	private JLabel lblRemainingTime;
	private JLabel lblElapsedTime;
	private JProgressBar progressBar;
	private JLabel lblSpeed;

	public OptimiserView() {

		setLayout(new MigLayout("", "[250:400][250:400,grow]", "[200:200:300,top][200:n,grow,top]"));

		JPanel pnlParameters = new JPanel();
		add(GUIUtil.frame("Parameters", pnlParameters), "cell 0 0,grow");
		pnlParameters.setLayout(new BorderLayout(0, 0));

		paramModel = new OptimiserParameterTableModel();
		tblParams = new JXTable(paramModel);
		tblParams.setFillsViewportHeight(true);
		tblParams.setRowSelectionAllowed(false);
		tblParams.packAll();

		JScrollPane scrollPane = new JScrollPane(tblParams);
		pnlParameters.add(scrollPane);

		JPanel pnlActions = new JPanel();
		pnlParameters.add(pnlActions, BorderLayout.SOUTH);

		btnRun = new JButton("Run Optimiser");
		pnlActions.add(btnRun);

		JPanel pnlStatus = new JPanel();
		add(GUIUtil.frame("Status", pnlStatus), "cell 1 0,grow");
		pnlStatus.setLayout(new BorderLayout(0, 0));

		JPanel pnlGrid = new JPanel();
		pnlStatus.add(pnlGrid);
		pnlGrid.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel pnlLeft = new JPanel();
		pnlLeft.setBorder(new EmptyBorder(3, 3, 3, 3));
		pnlGrid.add(pnlLeft);
		pnlLeft.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		pnlLeft.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		lblPermutations = new JLabel("Permutations: 0");
		panel_1.add(lblPermutations);

		JPanel panel_4 = new JPanel();
		pnlLeft.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		lblCompleted = new JLabel("Completed: 0");
		panel_4.add(lblCompleted, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		pnlLeft.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		lblElapsedTime = new JLabel("Elapsed Time: 0h 0m 0s");
		panel_2.add(lblElapsedTime);

		JPanel panel_3 = new JPanel();
		pnlLeft.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		lblRemainingTime = new JLabel("Remaining Time: 0h 0m 0s");
		panel_3.add(lblRemainingTime);
		
		JPanel panel_5 = new JPanel();
		pnlLeft.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		lblSpeed = new JLabel("Tests/min: 0");
		panel_5.add(lblSpeed, BorderLayout.CENTER);

		JPanel pnlRight = new JPanel();
		pnlRight.setBorder(new EmptyBorder(3, 3, 3, 3));
		pnlGrid.add(pnlRight);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 3, 3, 3));
		pnlStatus.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(146, 20));
		panel.add(progressBar);

		JPanel pnlResults = new JPanel();
		add(GUIUtil.frame("Results", pnlResults), "cell 0 1 2 1,grow");
		pnlResults.setLayout(new BorderLayout(0, 0));
		
		JScrollPane reportScrollPane = new JScrollPane();
		pnlResults.add(reportScrollPane);
		
		reportModel = new OptimiserReportTableModel();
		tblReport = new JXTable(reportModel);
		tblReport.setColumnControlVisible(true);
		tblReport.getColumnModel().getColumn(2).setCellRenderer(new ProfitCellRenderer(0));
		tblReport.getColumnModel().getColumn(3).setCellRenderer(new PriceCellRenderer(2));
		tblReport.getColumnModel().getColumn(4).setCellRenderer(new PriceCellRenderer(2));
		reportScrollPane.setViewportView(tblReport);
	}

	public List<OptimiserParameter> getParameters() {
		return paramModel.getParameters();
	}
	
	public JXTable getReportTable() {
		return tblReport;
	}
	
	public OptimiserReportTableModel getReportModel() {
		return reportModel;
	}
	
	public JButton getRunButton() {
		return btnRun;
	}

	public JLabel getLblPermutations() {
		return lblPermutations;
	}

	public JLabel getLblCompleted() {
		return lblCompleted;
	}

	public JLabel getLblRemainingTime() {
		return lblRemainingTime;
	}

	public JLabel getLblElapsedTime() {
		return lblElapsedTime;
	}
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JLabel getLblSpeed() {
		return lblSpeed;
	}
}
