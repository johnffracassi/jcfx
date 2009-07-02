package com.siebentag.proxy;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ProxyMonitor extends JFrame 
{
	private JPanel panel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();
	private JTextArea textArea = new JTextArea();
	private static Integer port = null;

	// basic class constructor
	public ProxyMonitor() 
	{
		try 
		{
			init();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		try 
		{
			port = new Integer(args[0]);
		} 
		catch (Exception e) 
		{
			port = new Integer(5000);
		}

		ProxyMonitor webserver = new ProxyMonitor();
	}

	private void init() throws Exception 
	{
		textArea.setBackground(new Color(16, 12, 66));
		textArea.setForeground(new Color(151, 138, 255));
		textArea.setBorder(BorderFactory.createLoweredBevelBorder());
		textArea.setToolTipText("");
		textArea.setEditable(false);

		// change this to impress your friends
		this.setTitle("Proxy Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add the various to the proper containers
		scrollPane.getViewport().add(textArea);
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane);
		getContentPane().add(panel, BorderLayout.CENTER);

		this.setSize(640, 480);
		this.validate();
		this.setVisible(true);

		new ProxyServer(port.intValue(), this);
	}

	public void sendMessageToWindow(String s) 
	{
		textArea.append(s);
	}
}