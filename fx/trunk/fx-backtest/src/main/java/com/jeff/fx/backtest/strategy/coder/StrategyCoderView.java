package com.jeff.fx.backtest.strategy.coder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;

import com.jeff.fx.gui.GUIUtil;
import java.awt.Color;

public class StrategyCoderView extends JPanel {
	
	private static final long serialVersionUID = 1364687392606846936L;

	private JTextArea txtOpenConditions;
	private JTextArea txtCloseConditions;
	private JTextArea txtCompilerOutput;
	private JTree treeDataModel;
	private JTextArea txtGenerated;
	private JButton btnSaveGenerated;
	private JButton btnCompile;
	private JTabbedPane tabbedPane;
	private JButton btnRunFromGenerated;
	private JToolBar toolBar_1;
	private JButton btnOpen;
	private JButton btnSaveSource;
	private JButton btnRunFromSource;
	private StrategyCodeParametersView scpv;
	private JSplitPane sh2;

	/**
	 * Create the panel.
	 */
	public StrategyCoderView() {
		
		setLayout(new BorderLayout());
		
		txtCompilerOutput = new JTextArea();
		txtCompilerOutput.setEditable(false);
		txtCompilerOutput.setTabSize(4);

		JScrollPane scrollCompilerOutput = new JScrollPane(txtCompilerOutput);
		scrollCompilerOutput.setPreferredSize(new Dimension(6, 150));
		scrollCompilerOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollCompilerOutput.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(GUIUtil.frame("Compiler Output", scrollCompilerOutput), BorderLayout.SOUTH);
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel pnlCode = new JPanel();
		pnlCode.setLayout(new BorderLayout(0, 0));
		JPanel pnlGeneratedCode = new JPanel();
		tabbedPane.addTab("Code", new ImageIcon(StrategyCoderView.class.getResource("/images/layout_edit.png")), pnlCode, null);
		tabbedPane.addTab("Generated Code", new ImageIcon(StrategyCoderView.class.getResource("/images/layout_link.png")), pnlGeneratedCode, null);
		pnlGeneratedCode.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		pnlGeneratedCode.add(toolBar, BorderLayout.NORTH);
		
		btnSaveGenerated = new JButton("");
		btnSaveGenerated.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/disk.png")));
		toolBar.add(btnSaveGenerated);
		
		btnCompile = new JButton("");
		btnCompile.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/cog_go.png")));
		toolBar.add(btnCompile);
		
		btnRunFromGenerated = new JButton("");
		btnRunFromGenerated.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/control_play_blue.png")));
		toolBar.add(btnRunFromGenerated);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlGeneratedCode.add(scrollPane_1, BorderLayout.CENTER);
		
		txtGenerated = new JTextArea();
		txtGenerated.setEditable(false);
		txtGenerated.setTabSize(4);
		scrollPane_1.setViewportView(txtGenerated);
		
		JSplitPane splitVertical = new JSplitPane();
		splitVertical.setResizeWeight(0.8);
		pnlCode.add(splitVertical);

		JSplitPane splitHorizontal = new JSplitPane();
		splitHorizontal.setOpaque(false);
		splitHorizontal.setForeground(Color.BLUE);
		splitHorizontal.setDividerSize(2);
		splitHorizontal.setResizeWeight(0.5);
		splitHorizontal.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitHorizontal.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitHorizontal.setPreferredSize(new Dimension(50, 50));
		splitHorizontal.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitVertical.setLeftComponent(splitHorizontal);
		txtCloseConditions = new JTextArea();
		txtCloseConditions.setTabSize(4);
		txtOpenConditions = new JTextArea();
		txtOpenConditions.setTabSize(4);
		splitHorizontal.setRightComponent(GUIUtil.frame("Close Conditions", new JScrollPane(txtCloseConditions)));
		splitHorizontal.setLeftComponent(GUIUtil.frame("Open Conditions", new JScrollPane(txtOpenConditions)));

		treeDataModel = new JTree();
		sh2 = new JSplitPane();
		sh2.setResizeWeight(0.33);
		sh2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		scpv = new StrategyCodeParametersView();
		scpv.setPreferredSize(new Dimension(140, 240));
		sh2.setLeftComponent(scpv);
		JScrollPane scrollPane = new JScrollPane(treeDataModel);
		sh2.setRightComponent(GUIUtil.frame("Data Model", scrollPane));
		splitVertical.setRightComponent(sh2);
		
		toolBar_1 = new JToolBar();
		pnlCode.add(toolBar_1, BorderLayout.NORTH);
		
		btnOpen = new JButton("");
		btnOpen.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/folder.png")));
		toolBar_1.add(btnOpen);
		
		btnSaveSource = new JButton("");
		btnSaveSource.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/disk.png")));
		toolBar_1.add(btnSaveSource);
		
		btnRunFromSource = new JButton("");
		btnRunFromSource.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/control_play_blue.png")));
		toolBar_1.add(btnRunFromSource);
		

	}

	public JTextArea getTxtOpenConditions() {
		return txtOpenConditions;
	}
	public JTextArea getTxtCloseConditions() {
		return txtCloseConditions;
	}
	public JTextArea getTxtCompilerOutput() {
		return txtCompilerOutput;
	}
	public JTree getTreeDataModel() {
		return treeDataModel;
	}
	public JTextArea getTxtGenerated() {
		return txtGenerated;
	}
	public JButton getBtnSaveGenerated() {
		return btnSaveGenerated;
	}
	public JButton getBtnCompile() {
		return btnCompile;
	}
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	public JButton getBtnRunFromGenerated() {
		return btnRunFromGenerated;
	}
	public JButton getBtnSaveSource() {
		return btnSaveSource;
	}
	public JButton getBtnOpen() {
		return btnOpen;
	}
	public JButton getBtnRunFromSource() {
		return btnRunFromSource;
	}
	public StrategyCodeParametersView getParametersView() {
		return scpv;
	}
	public JSplitPane getParamsSplit() {
		return sh2;
	}
}
