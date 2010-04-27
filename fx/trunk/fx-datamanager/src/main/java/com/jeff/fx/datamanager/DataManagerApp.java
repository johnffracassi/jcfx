package com.jeff.fx.datamanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataResponse;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.converter.TickToCandleConverter;
import com.jeff.fx.datastore.DataManager;

public class DataManagerApp {

	private static Logger log = Logger.getLogger(DataManagerApp.class);

	private DataManagerFrame frame = new DataManagerFrame();
	public static final ApplicationContext ctx;

	static {
		ctx = new ClassPathXmlApplicationContext(new String[] {"context-datastore.xml", "context-datamanager.xml"});
	}
	
	public static void main(String[] args) {
		new DataManagerApp();
	}
	
	public DataManagerApp() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}
}

class DataManagerFrame extends JFrame {

	private JComboBox cboDataSource = new JComboBox(FXDataSource.values());
	private JComboBox cboCurrencyPair = new JComboBox(Instrument.values());
	
	public DataManagerFrame() {
		setLayout(new BorderLayout());
		
		JPanel pnlControls = new JPanel(new FlowLayout());
		pnlControls.add(cboDataSource);
		pnlControls.add(cboCurrencyPair);
		
		final DataTableModel model = new DataTableModel();
		final JTable table = new JTable(model);
		final JScrollPane pnlTable = new JScrollPane(table);
		
		JButton btnLoad = new JButton("Load");
		pnlControls.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				DataLoadAction dla = new DataLoadAction();
				DateTime date = model.getDateForRow(table.getSelectedRow());
				dla.perform((FXDataSource)cboDataSource.getSelectedItem(), (Instrument)cboCurrencyPair.getSelectedItem(), date);
			}
		});
		
		add(pnlControls, BorderLayout.NORTH);
		add(pnlTable, BorderLayout.CENTER);
	}
}

class DataLoadAction {
	
	private static Logger log = Logger.getLogger(DataLoadAction.class);

	public void perform(FXDataSource dataSource, Instrument instrument, DateTime date) {
		try {
			DataManager dm = (DataManager) DataManagerApp.ctx.getBean("dataManager");
			FXDataResponse<TickDataPoint> response = dm.loadTicks(new FXDataRequest(dataSource, instrument, new Interval(date, date), Period.Tick));
			
			log.debug("Loaded " + response.getData().size() + " ticks");
			
			TickToCandleConverter t2c = new TickToCandleConverter();

			for (Period period : new Period[] { Period.OneMin, Period.FiveMin, Period.FifteenMin, Period.ThirtyMin, Period.OneHour }) {
				List<CandleDataPoint> candles = t2c.convert(response.getData(),period);
				dm.getCandleDataStore().store(candles);
			}
		} catch(Exception ex) {
			log.error(ex);
		}
	}
}

class DataTableModel extends DefaultTableModel  {
	
	private DataManager dataManager;
	
	public DataTableModel() {
		dataManager = (DataManager)DataManagerApp.ctx.getBean("dataManager");
	}
	
	public int getColumnCount() {
		return 2 + Period.values().length;
	}

	public String getColumnName(int arg0) {
		if(arg0 == 0) {
			return "Date";
		} else if(arg0 == Period.values().length + 1) {
			return "Load";
		} else {
			return Period.values()[arg0 - 1].key;
		}
	}

	public int getRowCount() {
		LocalDate startOfTime = new LocalDate(2010, 3, 1);
		LocalDate now = new LocalDate();
		
		Days days = Days.daysBetween(startOfTime, now);
		int dayCount = days.getDays();
		
		return dayCount;
	}

	public DateTime getDateForRow(int row) {
		DateTime now = new DateTime();
		now = now.minusDays(row);
		return new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0, 0);
	}
	
	public Object getValueAt(int row, int col) {
		
		DateTime now = getDateForRow(row);
		
		if(col == 0) {
			return now.toString("yyyy-MM-dd");
		} else if(col == 13) {
			return "Load";
		} else {
			FXDataRequest req = new FXDataRequest();
			req.setDataSource(FXDataSource.GAIN);
			req.setInstrument(Instrument.AUDUSD);
			req.setInterval(new Interval(now, now.plusDays(1)));
			req.setPeriod(Period.values()[col-1]);
			
			return (dataManager.exists(req) ? "Y":"N");
		}
	}
}