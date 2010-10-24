package com.jeff.fx.remote.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("FXRemote")
public class FXRemote {

	@Autowired
	private AvailabilityController availabilityController;
	
	private JFrame frame;
	private JTextField textField;

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context-remote.xml");
		FXRemote app = (FXRemote)ctx.getBean("FXRemote");
		app.run(); 
	}
	
	private void run()
	{
		frame.setVisible(true);
	}

	@PostConstruct
	private void init() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		textField = new JTextField("homer-1");
		panel.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("Publish");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				availabilityController.announceAsClient(textField.getText());
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab("Availability", null, availabilityController.getView(), null);
	}

}
