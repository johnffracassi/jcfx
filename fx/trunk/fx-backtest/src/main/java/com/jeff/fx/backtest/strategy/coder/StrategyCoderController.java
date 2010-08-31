package com.jeff.fx.backtest.strategy.coder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.engine.OrderBook;
import com.jeff.fx.backtest.orderbook.OrderBookController;
import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.CandleCollection;

public class StrategyCoderController {

	private StrategyCoderView view;
	private StrategyCompiler compiler;
	private OrderBookController orderBookController;
	private CandleCollection candles;
	private CodedStrategy compiledStrategy;
	private IndicatorCache indicators;
	
	public StrategyCoderController() {
		
		view = new StrategyCoderView();
		compiler = new StrategyCompiler();
		orderBookController = new OrderBookController();
		indicators = new IndicatorCache();
		
		view.getBtnCompile().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compile();
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
		
		view.getTabbedPane().addTab("Order Book", new ImageIcon(getClass().getResource("/images/book_open.png")), orderBookController.getView());
		
		// setup some initial default code
		view.getTxtOpenConditions().setText("if(candle.getDate().getDayOfWeek() == 3)\n\topen = true;\n");
		view.getTxtCloseConditions().setText("if(candle.getDate().getDayOfWeek() == 4)\n\tclose = true;\n");
		generate();
		compile();
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
		
		StrategyCodeModel model = new StrategyCodeModel();
		model.setClassName("Strategy1");
		model.setOpenCode(view.getTxtOpenConditions().getText());
		model.setCloseCode(view.getTxtCloseConditions().getText());
		
		StrategyCodeGenerator generator = new StrategyCodeGenerator();
		
		String content = generator.buildClass(model);
		view.getTxtGenerated().setText(content);
		
		compile();
	}
	
	private void saveSource() {
		System.out.println("save source");
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
