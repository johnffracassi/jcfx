package com.siebentag.fx.mv;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("marketVisualiser")
public class MarketVisualiser extends JFrame
{
	private static final long serialVersionUID = 89012751020092L;
	
	@Autowired
	private DataCriteria dataSelector;

	@Autowired
	private ExitAction exitAction;
	
	@Autowired
	private RefreshDataAction refreshDataAction;
	
	@Autowired
	private DataDisplay dataDisplay;
	
	@Autowired
	private DataSummaryPanel dataSummary;
	
	public static void main(String[] args)
    {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    MarketVisualiser app = (MarketVisualiser)ctx.getBean("marketVisualiser");
	    app.run();
    }
	
	public MarketVisualiser()
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
		setLayout(new VerticalFlowLayout(3));
		add(dataSelector);
		add(dataSummary);
		add(dataDisplay);
		
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.add(exitAction);
		
		JMenu actions = new JMenu("Action");
		actions.add(refreshDataAction);
		
		menuBar.add(file);
		menuBar.add(actions);
		
		setJMenuBar(menuBar);
		
		setSize(600, 800);
	}
}
