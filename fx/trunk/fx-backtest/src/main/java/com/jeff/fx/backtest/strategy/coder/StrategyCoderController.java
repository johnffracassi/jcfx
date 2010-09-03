package com.jeff.fx.backtest.strategy.coder;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.TooManyListenersException;

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
	private DataModelTreeModel treeModel;
	private File currentFile;
	
	public StrategyCoderController() {
		
		view = new StrategyCoderView();
		compiler = new StrategyCompiler();
		orderBookController = new OrderBookController();
		paramsController = new StrategyCodeParametersController();
		indicators = new IndicatorCache();
		fileManager = new StrategyCodeModelFileManager();
		generator = new StrategyCodeGenerator();
		treeModel = new DataModelTreeModel();

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
		
		view.getBtnSaveSourceAs().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSourceAs();
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
		view.getTxtOpenConditions().setText("");
		view.getTxtCloseConditions().setText("");
		generate();
		
		// setup the dataModel tree
		view.getDataModelTree().setModel(treeModel);
		view.getDataModelTree().setRootVisible(false);
		view.getDataModelTree().setCellRenderer(new DataModelTreeRenderer());
		
		// setup drop targets
		try {
			// TODO fix d&d drop target
			view.getTxtOpenConditions().getDropTarget().addDropTargetListener(new DropTargetAdapter() {
				  public void dragEnter(DropTargetDragEvent dtde) { dtde.acceptDrag(dtde.getDropAction()); }
				  public void dragOver(DropTargetDragEvent dtde) { dtde.acceptDrag(dtde.getDropAction()); }				  
				  public void drop(DropTargetDropEvent dtde) {
				    try {
				        Transferable tr = dtde.getTransferable();
				        DataFlavor flavor = new DataFlavor(Method.class, "Method Call");
			        	if (tr.isDataFlavorSupported(flavor)) {
				            dtde.acceptDrop(dtde.getDropAction());
				            Method method = (Method)tr.getTransferData(flavor);
				            dtde.dropComplete(true);
				            return;
				        }
				        dtde.rejectDrop();
					} catch (Exception e) {
					    e.printStackTrace();
					    dtde.rejectDrop();
					}
				}
			});
		} catch (TooManyListenersException e1) {
			// TODO handle this with a dialog
			e1.printStackTrace();
		}
		
		paramsController.addListener(treeModel);
	}
	
	private void runStrategy() {
		
		try {			
			if(candles == null) {
				candles = AppCtx.getDataManager().getCandles();
			}
			
			compiledStrategy.setId(1);
			compiledStrategy.setup(new HashMap<String, Object>(), indicators, candles);
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

		// get the preffered 
		
		// open the file chooser
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Open");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);

		int result = chooser.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				setModel(fileManager.importModel(file));
				currentFile = file;
			} catch (JAXBException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void saveSourceAs() {
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Save As...");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);

		int result = chooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				fileManager.exportModel(getModel(), file);
				currentFile = file;
			} catch (JAXBException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void saveSource() {
		
		if(currentFile == null) {
			saveSourceAs();
		} else {
			try {
				fileManager.exportModel(getModel(), currentFile);
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
		long startTime = System.nanoTime();
		try {
			compiledStrategy = (CodedStrategy)compiler.compile(className, content);
			view.getTxtCompilerOutput().setText(String.format("Compiled successfully (%.1fms)", (System.nanoTime() - startTime) / 1000000.0));
		} catch (Exception e) {
			view.getTxtCompilerOutput().setText(String.format("Compilation failed (%.1fms)\n%s", (System.nanoTime() - startTime) / 1000000.0, compiler.getOutput()));
		}
	}
	
	public StrategyCoderView getView() {
		return view;
	}
}


