package com.jeff.fx.backtest;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GenericDialog extends JDialog {
	
	private static final long serialVersionUID = -6792643327788512180L;

	private JPanel pnlEast;
	private JPanel pnlActions;
	private JPanel pnlContent;
	private JLabel lblTitle;
	private JPanel pnlWest;
	private JButton btnDone;
	private JButton btnCancel;
	
	public static final int DONE = 1;
	public static final int CANCEL = 2;
	private int value = 0;
	
	public GenericDialog(JPanel contentPanel, String title) {
		
		pnlContent = contentPanel;

		setSize(new Dimension(450, 275));
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		pnlActions = new JPanel();
		FlowLayout fl_pnlActions = (FlowLayout) pnlActions.getLayout();
		fl_pnlActions.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(pnlActions, BorderLayout.SOUTH);
		
		btnDone = new JButton("Done");
		pnlActions.add(btnDone);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close(CANCEL);
			}
		});
		pnlActions.add(btnCancel);
		
		JPanel pnlTitle = new JPanel();
		pnlTitle.setFocusable(false);
		pnlTitle.setForeground(UIManager.getColor("List.selectionForeground"));
		pnlTitle.setBackground(UIManager.getColor("List.selectionBackground"));
		getContentPane().add(pnlTitle, BorderLayout.NORTH);
		pnlTitle.setLayout(new BorderLayout(0, 0));
		
		lblTitle = new JLabel("Dialog Title");
		lblTitle.setBorder(new EmptyBorder(10, 10, 10, 10));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitle.setForeground(UIManager.getColor("List.selectionForeground"));
		lblTitle.setFocusable(false);
		pnlTitle.add(lblTitle);
		
		pnlWest = new JPanel();
		getContentPane().add(pnlWest, BorderLayout.WEST);
		pnlWest.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {}));
		
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		
		pnlEast = new JPanel();
		getContentPane().add(pnlEast, BorderLayout.EAST);
		pnlEast.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {}));
		
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				close(DONE);
			}
		});		

		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		lblTitle.setText(title);
		setTitle(title);
	}
	
	private void close(int value) {
		this.value = value;
		setVisible(false);
		dispose();
	}
	
	public boolean isCancelled() {
		return value == CANCEL;
	}
	
	public boolean isDone() {
		return value == DONE;
	}
	
	public GenericDialog() {
		this(new JXPanel(), "Dialog Box");
	}

	public JPanel getPnlEast() {
		return pnlEast;
	}
	
	public JPanel getPnlActions() {
		return pnlActions;
	}
	
	public JPanel getPnlContent() {
		return pnlContent;
	}
	
	public JLabel getLblTitle() {
		return lblTitle;
	}
	
	public JPanel getPnlWest() {
		return pnlWest;
	}
	
	public JButton getBtnDone() {
		return btnDone;
	}
}
