package com.siebentag.fx.tbv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.siebentag.fx.CandleStickDataPoint;
import com.siebentag.fx.backtest.BalanceSheet;
import com.siebentag.fx.backtest.OrderSide;
import com.siebentag.fx.mv.DoubleRenderer;
import com.siebentag.util.MultiThreadedExecutor;

public class TimeToTimeTable extends JXPanel
{
	private JXTable table;
	private JProgressBar progress;
	private TimeToTimeTableModel model;
	
	private List<CandleStickDataPoint> candles;
	private LocalTime startTime;
	private LocalDate startDate;
	private OrderSide orderSide;
	private double stop;
	
	
	public TimeToTimeTable()
	{
		model = new TimeToTimeTableModel();
		table = new JXTable(model);
		progress = new JProgressBar(0, 96);
		progress.setPreferredSize(new Dimension(500, 25));
		progress.setStringPainted(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(600, 450));
		
		JPanel pnlUpdateButton = new JPanel();
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				update();
			}
		});
		pnlUpdateButton.add(btnUpdate);
		pnlUpdateButton.add(progress);
		add(pnlUpdateButton, BorderLayout.NORTH);
		
		table.getColumn(0).setCellRenderer(new DateTimeRenderer());
		table.getColumn(1).setCellRenderer(new DateTimeRenderer());
		table.getColumn(2).setCellRenderer(new DoubleRenderer(4, false));
		table.getColumn(3).setCellRenderer(new DoubleRenderer(0, false));
		table.getColumn(4).setCellRenderer(new DoubleRenderer(0, false));
		table.getColumn(5).setCellRenderer(new DoubleRenderer(2, true));
	}

	public void update()
	{
		new SwingWorker() {
			protected Object doInBackground() throws Exception {
				model.setData(startDate, startTime, orderSide, stop, candles, progress);
				return null;
			}
		}.execute();
	}
	
	public void setCandles(LocalDate startDate, LocalTime startTime, OrderSide orderSide, double stop, List<CandleStickDataPoint> candles)
	{
		this.candles = candles;
		this.startTime = startTime;
		this.startDate = startDate;
		this.orderSide = orderSide;
		this.stop = stop;
	}
}

class TimeToTimeCalculator
{
	public static List<Row> calculate(final LocalDate startDate, final LocalTime startTime, final OrderSide orderSide, final double stop, final List<CandleStickDataPoint> candles, final JProgressBar progress)
	{
		final List<Row> rows = new ArrayList<Row>(96);
		final List<LocalTime> times = new ArrayList<LocalTime>(96);
		
		// generate times
		for(int hr = 0; hr < 24; hr ++)
		{
			for(int min = 0; min < 60; min += 15)
			{
				times.add(new LocalTime(hr, min));
			}
		}
		
		final long stime = System.currentTimeMillis();
		class TaskCompletionHandler
		{
			private int counter = 0;
			
			public synchronized void taskComplete()
			{
				counter ++;
				if(progress != null) 
				{
					final String message;
					
					double elapsed = (System.currentTimeMillis() - stime) / 1000.0;
					double total = elapsed / counter * 96;
					double remaining = total - elapsed;
					message = String.format("%d/%d (Elapsed=%.1fs / Remaining=%.1fs)", counter, 96, elapsed, remaining);
	
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							progress.setValue(counter);
							progress.setString(message);
						}
					});
				}
			}
		}

		// generate order books
		List<Runnable> tasks = new ArrayList<Runnable>(96);
		final TaskCompletionHandler completor = new TaskCompletionHandler();			
		for(final LocalTime t1 : times)
		{
			tasks.add(new Runnable() {
				public void run()
				{
					for(final LocalTime t2 : times)
					{
						BalanceSheet balanceSheet = OrderGenerator.createBalanceSheet(candles, orderSide, stop, startDate, t1, t2);
						Row row = new Row(t1, t2, orderSide, balanceSheet);
						rows.add(row);
					}
					completor.taskComplete();
				}
			});
		}

		MultiThreadedExecutor executor = new MultiThreadedExecutor(tasks);
		executor.run();
		
		progress.setString("Complete");
		
		return rows;
	}
}

class TimeToTimeTableModel extends DefaultTableModel
{
	private List<Row> rows;
	
	public void setData(LocalDate startDate, LocalTime startTime, OrderSide orderSide, double stop, List<CandleStickDataPoint> candles, JProgressBar progress)
	{
		rows = TimeToTimeCalculator.calculate(startDate, startTime, orderSide, stop, candles, progress);
		fireTableDataChanged();
	}

	public int getColumnCount()
	{
		return 6;
	}
	
	public Object getValueAt(int rowIdx, int column)
	{
		Row row = rows.get(rowIdx);
		
		switch(column)
		{
			case 0: return row.getOpen();
			case 1: return row.getClose();
			case 2: return row.getBalanceSheet().orderCount();
			case 3: return row.getBalanceSheet().getBalance();
			case 4: return row.getBalanceSheet().calculateMaximumDrawdown();
			case 5: return row.getBalanceSheet().calculateProfitForAllOrders() / row.getBalanceSheet().calculateMaximumDrawdown();
			default: return "ERROR";
		}
	}
	
	public Class<?> getColumnClass(int col)
	{
		switch(col)
		{
			case 0: return LocalTime.class;
			case 1: return LocalTime.class;
			case 2: return Double.class;
			case 3: return Double.class;
			case 4: return Double.class;
			case 5: return Double.class;
			default: return String.class;
		}
	}
	
	public String getColumnName(int col)
	{
		switch(col)
		{
			case 0: return "Open Time";
			case 1: return "Close Time";
			case 2: return "Trades";
			case 3: return "Balance";
			case 4: return "Drawdown";
			case 5: return "Bal/DD";
			default: return "ERROR";
		}
	}
	
	public int getRowCount()
	{
		return rows == null ? 0 : rows.size();
	}
}

class Row
{
	private LocalTime open;
	private LocalTime close;
	private OrderSide orderSide;
	private BalanceSheet balanceSheet;
	
	public Row(LocalTime open, LocalTime close, OrderSide orderSide, BalanceSheet balanceSheet)
	{
		super();
		this.open = open;
		this.close = close;
		this.orderSide = orderSide;
		this.balanceSheet = balanceSheet;
	}

	public OrderSide getOrderSide()
	{
		return orderSide;
	}
	
	public LocalTime getOpen()
	{
		return open;
	}
	
	public LocalTime getClose()
	{
		return close;
	}
	
	public BalanceSheet getBalanceSheet()
	{
		return balanceSheet;
	}
}