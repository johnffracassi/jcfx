package com.jeff.fx.backtest.strategy.coder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.orderbook.OrderBookController;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.gui.GUIUtil;

public class StrategyCoderController {

	private StrategyCoderView view;
	private StrategyCompiler compiler;
	private StrategyCodeGenerator generator;
	private StrategyCodeModelFileManager fileManager;
	private OrderBookController orderBookController;
	private StrategyCodeParametersController paramsController;
	private CandleCollection candles;
	private CodedStrategy compiledStrategy;
	private IndicatorCache indicators;
	
	public StrategyCoderController() {
		
		view = new StrategyCoderView();
		compiler = new StrategyCompiler();
		orderBookController = new OrderBookController();
		paramsController = new StrategyCodeParametersController();
		indicators = new IndicatorCache();
		fileManager = new StrategyCodeModelFileManager();
		generator = new StrategyCodeGenerator();

		view.getBtnGenerate().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generate();
			}
		});
		
		view.getBtnCompile().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compile();
			}
		});
		
		view.getBtnOpen().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSource();
			}
		});
		
		view.getBtnSaveSource().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSource();
			}
		});
		
		view.getBtnSaveGenerated().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveGenerated();
			}
		});
		
		view.getBtnRunFromGenerated().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runStrategy();
			}
		});
		
		view.getBtnRunFromSource().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runStrategy();
			}
		});
		
		view.getTxtOpenConditions().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				generate();
			}
		});
		
		view.getTxtCloseConditions().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				generate();
			}
		});
		
		view.getTabbedPane().addTab("Strategy Test", new ImageIcon(getClass().getResource("/images/book_open.png")), orderBookController.getView());
		view.getParamsSplit().setLeftComponent(GUIUtil.frame("Parameters", paramsController.getView()));
		
		// setup some initial default code
		view.getTxtOpenConditions().setText("if(candle.getDate().getDayOfWeek() == 3)\n\topen = true;\n");
		view.getTxtCloseConditions().setText("if(candle.getDate().getDayOfWeek() == 4)\n\tclose = true;\n");
		generate();
	}
	
	private void runStrategy() {
		
		try {			
			if(candles == null) {
				candles = AppCtx.getDataManager().getCandles();
			}
			
			compiledStrategy.setId(1);
			compiledStrategy.setIndicators(indicators);
			compiledStrategy.setParam(new HashMap<String, Object>());
			OrderBook book = compiledStrategy.execute(candles);
			orderBookController.update(candles, book);
		
			view.getTabbedPane().setSelectedIndex(2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generate() {
		String content = generator.buildClass(getModel());
		view.getTxtGenerated().setText(content);
		compile();
	}
	
	private StrategyCodeModel getModel() {
		StrategyCodeModel model = new StrategyCodeModel();
		model.setClassName("Strategy1");
		model.setOpenCode(view.getTxtOpenConditions().getText());
		model.setCloseCode(view.getTxtCloseConditions().getText());
		model.setParameters(paramsController.getParams());
		return model;
	}
	
	private void setModel(StrategyCodeModel model) {
		view.getTxtOpenConditions().setText(model.getOpenCode());
		view.getTxtCloseConditions().setText(model.getCloseCode());
		paramsController.setParams(model.getParameters());
	}
	
	private void openSource() {

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Open");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);

		int result = chooser.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				setModel(fileManager.importModel(file));
			} catch (JAXBException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void saveSource() {
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Save As...");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);

		int result = chooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				fileManager.exportModel(getModel(), file);
			} catch (JAXBException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void saveGenerated() {
		System.out.println("save generated");
	}
	
	private void compile() {
		
		String className = "Strategy1";
		String content = view.getTxtGenerated().getText();
		try {
			compiledStrategy = (CodedStrategy)compiler.compile(className, content);
			view.getTxtCompilerOutput().setText("Compiled successfully");
		} catch (Exception e) {
			view.getTxtCompilerOutput().setText("Errors:\n" + compiler.getOutput());
		}
	}
	
	public StrategyCoderView getView() {
		return view;
	}
}
