package com.jeff.fx.backtest;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import com.jeff.fx.action.AboutAction;
import com.jeff.fx.action.ExitAction;
import com.jeff.fx.backtest.chart.NewCandleChartAction;
import com.jeff.fx.backtest.strategy.simple.NewSimpleStrategyChartAction;
import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class BackTestFrame extends JXFrame {
	
	private JTabbedPane tabs = new JTabbedPane();
	
	public void init() {
		setSize(1200, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JXPanel pnlWest = new JXPanel(new VerticalFlowLayout(3));
		pnlWest.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		
		DatasetDefinitionPanel ddp = new DatasetDefinitionPanel("newChart");
		pnlWest.add(frame("Dataset", ddp));

        add(tabs, BorderLayout.CENTER);
        add(pnlWest, BorderLayout.WEST);
        
        JToolBar toolBar = new JToolBar("Main");
        toolBar.add(new AboutAction());
        toolBar.add(new NewCandleChartAction());
        toolBar.add(new NewSimpleStrategyChartAction());
        setToolBar(toolBar);
        
        setJMenuBar(buildMenu());
    }
	
	public void addMainPanel(JPanel panel, String title) {
		tabs.add(panel, title);
		tabs.setTabComponentAt(tabs.getTabCount()-1, new ButtonTabComponent(tabs));
		invalidate();
	}
	
	private JMenuBar buildMenu() {
		// Menu setup
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new ExitAction());		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new AboutAction());
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	
	private JXPanel frame(String title, JPanel pnl) {
        JXTitledPanel titled = new JXTitledPanel(title, pnl);
        Border shadow = new DropShadowBorder(Color.BLACK, 3, 0.66f, 3, false, false, true, true);
        Border line = BorderFactory.createLineBorder(Color.GRAY);
        Border border = BorderFactory.createCompoundBorder(shadow, line);
        titled.setBorder(border);
        return titled;
	}
}
