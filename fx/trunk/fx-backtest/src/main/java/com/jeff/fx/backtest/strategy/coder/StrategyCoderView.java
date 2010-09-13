package com.jeff.fx.backtest.strategy.coder;

import java.awt.BorderLayout;
import java.awt.Color;
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
	private JToolBar toolBarCode;
	private JButton btnOpen;
	private JButton btnSaveSource;
	private JButton btnRunFromSource;
	private StrategyCodeParametersView scpv;
	private JSplitPane splitParamsAndDataModel;
	private JButton btnSaveSourceAs;
	private JButton btnGenerate;

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
		add(GUIUtil.frame("Compiler Output", scrollCompilerOutput),BorderLayout.SOUTH);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel pnlCode = new JPanel();
		pnlCode.setLayout(new BorderLayout(0, 0));
		JPanel pnlGeneratedCode = new JPanel();
		tabbedPane.addTab("Code", new ImageIcon(StrategyCoderView.class.getResource("/images/layout_edit.png")), pnlCode, null);
		tabbedPane.addTab("Generated Code",new ImageIcon(StrategyCoderView.class.getResource("/images/layout_link.png")),pnlGeneratedCode, null);
		pnlGeneratedCode.setLayout(new BorderLayout(0, 0));

		JToolBar toolBarGenerated = new JToolBar();
		pnlGeneratedCode.add(toolBarGenerated, BorderLayout.NORTH);

		btnSaveGenerated = new JButton("Save As...");
		btnSaveGenerated.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/disk.png")));
		toolBarGenerated.add(btnSaveGenerated);

		btnGenerate = new JButton("Generate");
		btnGenerate.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/cog_edit.png")));
		toolBarGenerated.add(btnGenerate);

		btnCompile = new JButton("Compile");
		btnCompile.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/cog_go.png")));
		toolBarGenerated.add(btnCompile);

		btnRunFromGenerated = new JButton("Run");
		btnRunFromGenerated.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/control_play_blue.png")));
		toolBarGenerated.add(btnRunFromGenerated);

		JScrollPane scrollGenerated = new JScrollPane();
		scrollGenerated.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollGenerated.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlGeneratedCode.add(scrollGenerated, BorderLayout.CENTER);

		txtGenerated = new JTextArea();
		txtGenerated.setEditable(false);
		txtGenerated.setTabSize(4);
		scrollGenerated.setViewportView(txtGenerated);

		JSplitPane splitVertical = new JSplitPane();
		splitVertical.setContinuousLayout(true);
		splitVertical.setResizeWeight(0.85);
		pnlCode.add(splitVertical);

		JSplitPane splitCode = new JSplitPane();
		splitCode.setOpaque(false);
		splitCode.setForeground(Color.BLUE);
		splitCode.setDividerSize(2);
		splitCode.setResizeWeight(0.5);
		splitCode.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitCode.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitCode.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitVertical.setLeftComponent(splitCode);
		txtCloseConditions = new JTextArea();
		txtCloseConditions.setTabSize(4);
		txtOpenConditions = new JTextArea();
		txtOpenConditions.setTabSize(4);
		splitCode.setRightComponent(GUIUtil.frame("Close Conditions",new JScrollPane(txtCloseConditions)));
		splitCode.setLeftComponent(GUIUtil.frame("Open Conditions",new JScrollPane(txtOpenConditions)));

		treeDataModel = new JTree();
		treeDataModel.setShowsRootHandles(true);
		treeDataModel.setAutoscrolls(true);
		treeDataModel.setDragEnabled(true);
		splitParamsAndDataModel = new JSplitPane();
		splitParamsAndDataModel.setResizeWeight(0.33);
		splitParamsAndDataModel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		scpv = new StrategyCodeParametersView();
		scpv.setPreferredSize(new Dimension(140, 240));
		splitParamsAndDataModel.setLeftComponent(scpv);
		JScrollPane scrollPane = new JScrollPane(treeDataModel);
		splitParamsAndDataModel.setRightComponent(GUIUtil.frame("Data Model",scrollPane));
		splitVertical.setRightComponent(splitParamsAndDataModel);
		splitVertical.setDividerLocation(700);

		toolBarCode = new JToolBar();
		pnlCode.add(toolBarCode, BorderLayout.NORTH);

		btnOpen = new JButton("Open");
		btnOpen.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/folder.png")));
		toolBarCode.add(btnOpen);

		btnSaveSource = new JButton("Save");
		btnSaveSource.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/disk.png")));
		toolBarCode.add(btnSaveSource);

		btnSaveSourceAs = new JButton("Save As...");
		btnSaveSourceAs.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/disk.png")));
		toolBarCode.add(btnSaveSourceAs);

		btnRunFromSource = new JButton("Run");
		btnRunFromSource.setIcon(new ImageIcon(StrategyCoderView.class.getResource("/images/control_play_blue.png")));
		toolBarCode.add(btnRunFromSource);

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

	public JTree getDataModelTree() {
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
		return splitParamsAndDataModel;
	}

	public JButton getBtnGenerate() {
		return btnGenerate;
	}

	public JButton getBtnSaveSourceAs() {
		return btnSaveSourceAs;
	}
}
