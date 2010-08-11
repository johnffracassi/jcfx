package com.jeff.fx.backtest;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;

import com.jeff.fx.action.AboutAction;
import com.jeff.fx.action.ClearCacheAction;
import com.jeff.fx.action.ExitAction;
import com.jeff.fx.backtest.chart.NewCandleChartAction;
import com.jeff.fx.backtest.strategy.time.NewTimeStrategyChartAction;
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
		pnlWest.add(GUIUtil.frame("Dataset", ddp));

        add(tabs, BorderLayout.CENTER);
        add(pnlWest, BorderLayout.WEST);
        
        JToolBar toolBar = new JToolBar("Main");
        toolBar.add(new AboutAction());
        toolBar.add(new NewCandleChartAction());
        toolBar.add(new NewTimeStrategyChartAction());
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
		fileMenu.add(new ClearCacheAction());
		fileMenu.add(new JSeparator());
		fileMenu.add(new ExitAction());		
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new AboutAction());
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		return menuBar;
	}
}
