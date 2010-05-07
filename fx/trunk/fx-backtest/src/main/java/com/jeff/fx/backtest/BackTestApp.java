package com.jeff.fx.backtest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jfree.chart.ChartPanel;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.DataManager;
import com.siebentag.gui.VerticalFlowLayout;

@Component("backTestApp")
public class BackTestApp {
	
	private static Logger log = Logger.getLogger(BackTester.class);

	private BackTestFrame frame;
	
	@Autowired
	private DataManager dataManager;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
		BackTestApp app = (BackTestApp)ctx.getBean("backTestApp");
		app.run();
	}

	private void run() {
		log.info("Starting application");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		frame = new BackTestFrame();
		frame.init();
		frame.setVisible(true);
		
		AppCtx.register(NewChartEvent.class, new ActionEventListener() {
			public void event(com.jeff.fx.backtest.ActionEvent ev) {
				try {
					FXDataSource dataSource = FXDataSource.valueOf(AppCtx.getString("newChart.dataSource"));
					Instrument instrument = Instrument.valueOf(AppCtx.getString("newChart.instrument"));
					Period period = Period.valueOf(AppCtx.getString("newChart.period"));
					FXDataRequest request = new FXDataRequest(dataSource, instrument, AppCtx.getDate("newChart.startDate"), AppCtx.getDate("newChart.endDate"), period);
					FXDataResponse<CandleDataPoint> candles = dataManager.loadCandles(request);
					ChartPanel chart = CandleChart.createChart(instrument + " (" + period.key + ")", candles.getData());
					frame.addMainPanel(chart, instrument + " (" + period.key + ")");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}

@SuppressWarnings("serial")
abstract class PComboBox extends JComboBox {
	
	public abstract Object resolve(String str);
	
	public PComboBox(final Object[] values, final String key) {
		
		super(values);
		
		String val = AppCtx.getString(key);
		setSelectedItem(resolve(val));
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.set(key, String.valueOf(getSelectedItem()));
			}
		});
	}
}

@SuppressWarnings("serial")
class ChartDefinitionPanel extends JXPanel {
	
	public ChartDefinitionPanel() {
		setLayout(new VerticalFlowLayout(3));
		
		JXPanel pnlDataSource = new JXPanel();
		pnlDataSource.add(new JXLabel("Data Source"));
		pnlDataSource.add(new PComboBox(FXDataSource.values(), "newChart.dataSource") {
			public Object resolve(String str) { return (str == null) ? (FXDataSource.values()[0]) : (FXDataSource.valueOf(str)); }
		});
		add(pnlDataSource);
		
		JXPanel pnlInstrument = new JXPanel();
		pnlInstrument.add(new JXLabel("Instrument"));
		pnlInstrument.add(new PComboBox(Instrument.values(), "newChart.instrument") {
			public Object resolve(String str) { return (str == null) ? (Instrument.values()[0]) : (Instrument.valueOf(str)); }
		});
		add(pnlInstrument);		
		
		JXPanel pnlPeriod = new JXPanel();
		pnlPeriod.add(new JXLabel("Period"));
		pnlPeriod.add(new PComboBox(Period.values(), "newChart.period") {
			public Object resolve(String str) { return (str == null) ? (Period.values()[0]) : (Period.valueOf(str)); }
		});
		add(pnlPeriod);

		JXPanel pnlStartDate = new JXPanel();
		pnlStartDate.add(new JXLabel("Start"));
		final JXDatePicker startDate = new JXDatePicker(new Date(System.currentTimeMillis() - (3*24*60*60*1000)));
		pnlStartDate.add(startDate);
		startDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.set("newChart.startDate", new LocalDate(startDate.getDate()));
			}
		});
		add(pnlStartDate);
		
		JXPanel pnlEndDate = new JXPanel();
		pnlEndDate.add(new JXLabel("End"));
		final JXDatePicker endDate = new JXDatePicker(new Date());
		pnlEndDate.add(endDate);
		endDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				AppCtx.set("newChart.endDate", new LocalDate(endDate.getDate()));
			}
		});
		add(pnlEndDate);
		
		JXPanel pnlActions = new JXPanel();
		JXButton btnNewChart = new JXButton(new NewChartAction());
		pnlActions.add(btnNewChart);
		add(pnlActions);
	}
}

@SuppressWarnings("serial")
class BackTestFrame extends JXFrame implements ActionEventListener {
	
	private JTabbedPane tabs = new JTabbedPane();
	
	public void init() {
		setSize(1200, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JXPanel pnlWest = frame("New Chart", new ChartDefinitionPanel());

        JXPanel pnlSouth = new JXPanel(new BorderLayout());
        pnlSouth.add(frame("South", new JXPanel()), BorderLayout.CENTER);

        add(tabs, BorderLayout.CENTER);
        add(pnlWest, BorderLayout.WEST);
        add(pnlSouth, BorderLayout.SOUTH);	
        
        setJMenuBar(buildMenu());
        
        AppCtx.register(NewChartEvent.class, this);
    }
	
	public void addMainPanel(ChartPanel panel, String title) {
		tabs.add(panel, title);
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

	public void event(com.jeff.fx.backtest.ActionEvent ev) {
		System.out.println("Event!!!");
	}
}

