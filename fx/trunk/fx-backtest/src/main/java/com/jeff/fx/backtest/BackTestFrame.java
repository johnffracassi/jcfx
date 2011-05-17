package com.jeff.fx.backtest;

import com.jeff.fx.action.AboutAction;
import com.jeff.fx.action.ExitAction;
import com.jeff.fx.backtest.action.ApplicationPreferencesAction;
import com.jeff.fx.backtest.action.ClearCacheAction;
import com.jeff.fx.backtest.chart.NewCandleChartAction;
import com.jeff.fx.backtest.chart.NewPriceChartAction;
import com.jeff.fx.backtest.strategy.coder.NewStrategyCoderAction;
import com.jeff.fx.backtest.strategy.time.NewTimeStrategyAction;
import com.jeff.fx.gui.ButtonTabComponent;
import com.jeff.fx.gui.GUIUtil;
import com.siebentag.gui.VerticalFlowLayout;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
@Component
public class BackTestFrame extends JXFrame {
	
	private JTabbedPane tabs = new JTabbedPane();
	
	@Autowired private ClearCacheAction clearCacheAction;	
	@Autowired private ExitAction exitAction;	
	@Autowired private AboutAction aboutAction;
	@Autowired private NewTimeStrategyAction newTimeStrategyAction;
	@Autowired private NewStrategyCoderAction newStrategyCoderAction;
	@Autowired private ApplicationPreferencesAction applicationPreferencesAction;
	@Autowired private NewPriceChartAction newPriceChartAction;
	@Autowired private NewCandleChartAction newCandleChartAction;

	@PostConstruct
	public void init() {
		
		setSize(1200, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JXPanel pnlWest = new JXPanel(new VerticalFlowLayout(3));
		pnlWest.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		
		DatasetDefinitionPanel ddp = new DatasetDefinitionPanel("newChart");
		pnlWest.add(GUIUtil.frame("Dataset", ddp));
		
		// add left and right components
        add(tabs, BorderLayout.CENTER);
        add(pnlWest, BorderLayout.WEST);
        
        // add menu and toolbar
        setToolBar(buildToolBar());
        setJMenuBar(buildMenu());
    }
	
	public void addMainPanel(JPanel panel, String title) {
		
		tabs.add(panel, title);
		tabs.setTabComponentAt(tabs.getTabCount()-1, new ButtonTabComponent(tabs));
		invalidate();
	}
	
	private JToolBar buildToolBar() {
		
        JToolBar toolBar = new JToolBar("Main");
        toolBar.add(newTimeStrategyAction);
        toolBar.add(newStrategyCoderAction);
        toolBar.add(newPriceChartAction);
        toolBar.add(newCandleChartAction);
        return toolBar;
	}
	
	private JMenuBar buildMenu() {

		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(clearCacheAction);
		fileMenu.add(applicationPreferencesAction);
		fileMenu.add(new JSeparator());
		fileMenu.add(exitAction);		
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(aboutAction);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		return menuBar;
	}
}
