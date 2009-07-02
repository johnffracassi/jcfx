package com.siebentag.fx.tbv;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.backtest.BalanceSheet;
import com.siebentag.fx.backtest.OrderSide;
import com.siebentag.fx.loader.CandleDataDAO;
import com.siebentag.fx.mv.ExitAction;
import com.siebentag.fx.mv.VerticalFlowLayout;
import com.siebentag.fx.source.FXDataSource;
import com.siebentag.fx.source.Instrument;

@Component("timeBasedVisualiser")
public class TimeBasedVisualiser extends JFrame
{
	private static final long serialVersionUID = 89012751020092L;
	
	@Autowired
	private ExitAction exitAction;
	
	@SuppressWarnings("unused")
	@Autowired
	private CandleDataDAO dao;
	
	private List<CandleStickDataPoint> selectedCandles = Collections.<CandleStickDataPoint>emptyList();
	private DataSummaryPanel dataSummaryPanel = new DataSummaryPanel();
	private OrdersTable dataDisplayPanel = new OrdersTable();
	private BalanceSheetChart balanceSheetChart = new BalanceSheetChart();
	private TimeValueTable timeValueTable = new TimeValueTable();
	private TimeToTimeTable timeToTimeTable = new TimeToTimeTable();

	public static void main(String[] args)
    {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    TimeBasedVisualiser app = (TimeBasedVisualiser)ctx.getBean("timeBasedVisualiser");
	    app.run();
    }
	
	public TimeBasedVisualiser()
	{
	}
	
	public void run()
	{
		init();
		setVisible(true);
	}
	
	private void init()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel leftUpperPanel = new JPanel(new VerticalFlowLayout(3));
		JPanel rightPanel = new JPanel(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.add(exitAction);
		
		JMenu actions = new JMenu("Action");
//		actions.add(refreshDataAction);
		
		menuBar.add(file);
		menuBar.add(actions);
		
		setJMenuBar(menuBar);
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.add("Orders", dataDisplayPanel);
		tabPane.add("Time Values", timeValueTable);
		tabPane.add("Time2Time", timeToTimeTable);
		
		leftUpperPanel.add(new TBVDataSetup(this));
		leftUpperPanel.add(dataSummaryPanel);
		leftPanel.add(leftUpperPanel, BorderLayout.NORTH);
		leftPanel.add(tabPane, BorderLayout.CENTER);
		rightPanel.add(balanceSheetChart);
		
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
		
		setSize(1600, 1000);
	}
	
	public void reloadData(Instrument instrument)
	{
		selectedCandles = CandleDataDAO.findCandles(FXDataSource.Forexite, instrument, String.valueOf(900));
		System.out.println("Found " + selectedCandles.size() + " candles");
		timeValueTable.setCandles(null, selectedCandles);
		timeToTimeTable.setCandles(null, new LocalTime(0,0), OrderSide.Buy, 0.0, selectedCandles);
	}
	
	public void parametersChanged(TBVDataSetup setup)
	{
		BalanceSheet balanceSheet = OrderGenerator.createBalanceSheet(selectedCandles, setup.getOrderSide(), setup.getStop(), setup.getStartDate(), setup.getStartTime(), setup.getEndTime());
		dataSummaryPanel.setBalanceSheet(balanceSheet);
		dataDisplayPanel.setBalanceSheet(balanceSheet);
		balanceSheetChart.setBalanceSheet(balanceSheet);
		timeValueTable.setCandles(setup.getStartDate(), selectedCandles);
		timeToTimeTable.setCandles(setup.getStartDate(), setup.getStartTime(), setup.getOrderSide(), setup.getStop(), selectedCandles);
	}
}
