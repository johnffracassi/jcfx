package com.jeff.fx.datamanager;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
