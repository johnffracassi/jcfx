package com.jeff.fx.backtest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXLabel;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Cursor;
import java.awt.ComponentOrientation;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
class ProgressPanel extends JFrame {

	private JProgressBar progressBar;
	private JXLabel lblMessage;
	private JLabel lblTaskHeading;
	private JPanel pnlProgress;
	private JPanel pnlContent;
	
	protected ProgressPanel() {
		setAlwaysOnTop(true);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setLocationByPlatform(true);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setUndecorated(true);
		setTitle("Progress");		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 100);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		pnlContent = new JPanel();
		pnlContent.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlMessages = new JPanel();
		pnlContent.add(pnlMessages, BorderLayout.CENTER);
		pnlMessages.setLayout(new BorderLayout(0, 0));
		
		lblTaskHeading = new JLabel("Task Heading");
		lblTaskHeading.setBorder(new EmptyBorder(3, 3, 0, 0));
		lblTaskHeading.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlMessages.add(lblTaskHeading, BorderLayout.NORTH);
		
		lblMessage = new JXLabel();
		lblMessage.setBorder(new EmptyBorder(7, 7, 7, 7));
		lblMessage.setLineWrap(true);
		lblMessage.setVerticalAlignment(SwingConstants.TOP);
		pnlMessages.add(lblMessage);
		lblMessage.setText("This is some sample test");
		lblMessage.setPreferredSize(new Dimension(300, 60));
		
		JLabel lblIcon = new JLabel("");
		pnlContent.add(lblIcon, BorderLayout.WEST);
		lblIcon.setPreferredSize(new Dimension(60, 100));
		lblIcon.setHorizontalTextPosition(SwingConstants.LEADING);
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(new ImageIcon(ProgressPanel.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		
		pnlProgress = new JPanel();
		pnlContent.add(pnlProgress, BorderLayout.SOUTH);
		pnlProgress.setBorder(new EmptyBorder(4, 4, 4, 4));
		pnlProgress.setLayout(new BorderLayout(0, 0));
		
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(146, 20));
		pnlProgress.add(progressBar);
	}

	public String getTaskHeading() {
		return lblTaskHeading.getText();
	}
	public void setTaskHeading(String text) {
		lblTaskHeading.setText(text);
	}
	public String getMessage() {
		return lblMessage.getText();
	}
	public void setMessage(String text_1) {
		lblMessage.setText(text_1);
	}
	public int getProgress() {
		return progressBar.getValue();
	}
	public void setProgress(int value) {
		progressBar.setValue(value);
	}
	public int getMaximum() {
		return progressBar.getMaximum();
	}
	public void setMaximum(int maximum) {
		progressBar.setMaximum(maximum);
	}
}
