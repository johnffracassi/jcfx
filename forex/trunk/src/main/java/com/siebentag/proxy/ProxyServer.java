package com.siebentag.proxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer extends Thread 
{
	private ProxyMonitor monitor; // the starter class, needed for gui
	private int port; // port we are going to listen to

	public ProxyServer(int listen_port, ProxyMonitor to_send_message_to) 
	{
		monitor = to_send_message_to;
		port = listen_port;
		this.start();
	}
	
	private void alert(String s2) 
	{ 
		monitor.sendMessageToWindow(s2);
	}

	public void run() 
	{
		ServerSocket serversocket = null;
		
		alert("Starting proxy thread");

		try 
		{
			alert("Trying to bind to localhost on port " + Integer.toString(port) + "...");
			serversocket = new ServerSocket(port);
		} 
		catch (Exception e) 
		{
			alert("\nFatal Error:" + e.getMessage());
			return;
		}
		
		alert("OK!\n");
		while (true) 
		{
			alert("\nReady, Waiting for requests...\n");
		
			try 
			{
				// this call waits/blocks until someone connects to the port we are listening to
				Socket connectionsocket = serversocket.accept();
			
				// figure out what ipaddress the client commes from, just for show!
				InetAddress client = connectionsocket.getInetAddress();
				
				// and print it to gui
				alert(client.getHostName() + " connected to ProxyServer.\n");
				
				// Read the http request from the client from the socket interface into a buffer.
				BufferedReader input = new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
				
				// Prepare a outputstream from us to the client, this will be used sending back 
				// our response (header + requested file) to the client.
				DataOutputStream output = new DataOutputStream(connectionsocket.getOutputStream());

				// as the name suggest this method handles the http request, see
				// further down. abstraction rules
				handleRequest(input, output);
			} 
			catch (Exception e) 
			{
				alert("\nError:" + e.getMessage());
			}
		}
	}

	/**
	 * our implementation of the hypertext transfer protocol
	 * 
	 * @param input
	 * @param output
	 */
	private void handleRequest(BufferedReader input, DataOutputStream output) 
	{
		int method = 0; // 1 get, 2 head, 0 not supported
		String http = new String(); // a bunch of strings to hold
		String path = new String(); // the various things, what http v, what path,
		String file = new String(); // what file
		String user_agent = new String(); // what user_agent

		try 
		{
			// This is the two types of request we can handle
			// GET /index.html HTTP/1.0
			// HEAD /index.html HTTP/1.0
			String tmp = input.readLine(); // read from the stream
			String tmp2 = new String(tmp);
			tmp.toUpperCase(); // convert it to uppercase
			if (tmp.startsWith("GET")) { // compare it is it GET
				method = 1;
			} // if we set it to method 1
			if (tmp.startsWith("HEAD")) { // same here is it HEAD
				method = 2;
			} // set method to 2

			if (method == 0) { // not supported
				try {
					output.writeBytes(construct_http_header(501, 0));
					output.close();
					return;
				} catch (Exception e3) { // if some error happened catch it
					alert("error:" + e3.getMessage());
				} // and display error
			}
			// }

			// tmp contains "GET /index.html HTTP/1.0 ......."
			// find first space
			// find next space
			// copy whats between minus slash, then you get "index.html"
			// it's a bit of dirty code, but bear with me...
			int start = 0;
			int end = 0;
			for (int a = 0; a < tmp2.length(); a++) {
				if (tmp2.charAt(a) == ' ' && start != 0) {
					end = a;
					break;
				}
				if (tmp2.charAt(a) == ' ' && start == 0) {
					start = a;
				}
			}
			path = tmp2.substring(start + 2, end); // fill in the path
		} catch (Exception e) {
			alert("errorr" + e.getMessage());
		} // catch any exception

		// path do now have the filename to what to the file it wants to open
		alert("\nClient requested:" + new File(path).getAbsolutePath() + "\n");
		FileInputStream requestedfile = null;

		try 
		{
			requestedfile = new FileInputStream(path);
		} 
		catch (Exception e) 
		{
			try 
			{
				// if you could not open the file send a 404
				output.writeBytes(construct_http_header(404, 0));
			
				// close the stream
				output.close();
			} 
			catch (Exception e2) 
			{
				alert("Error: " + e2.getMessage());
			}
			
			alert("Error: " + e.getMessage());
		}

		// happy day scenario
		try 
		{
			int type_is = 0;
			
			// find out what the filename ends with, so you can construct a the right content type
			if(path.endsWith(".zip") || path.endsWith(".exe") || path.endsWith(".tar")) 
			{
				type_is = 3;
			}
			else if(path.endsWith(".jpg") || path.endsWith(".jpeg")) 
			{
				type_is = 1;
			}
			else if(path.endsWith(".gif")) 
			{
				type_is = 2;
			}
			output.writeBytes(construct_http_header(200, 5));

			// if it was a HEAD request, we don't print any BODY
			if(method == 1) 
			{
				int bytesRead = 0;
				while((bytesRead = requestedfile.read()) >= 0) 
				{
					output.write(bytesRead);
				}
			}

			// clean up the files, close open handles
			output.close();
			requestedfile.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private String construct_http_header(int return_code, int file_type) 
	{
		String s = "HTTP/1.0 ";
		
		switch (return_code) 
		{
			case 200:
				s = s + "200 OK";
				break;
			case 400:
				s = s + "400 Bad Request";
				break;
			case 403:
				s = s + "403 Forbidden";
				break;
			case 404:
				s = s + "404 Not Found";
				break;
			case 500:
				s = s + "500 Internal Server Error";
				break;
			case 501:
				s = s + "501 Not Implemented";
				break;
		}

		s = s + "\r\n";
		s = s + "Connection: close\r\n";
		s = s + "Server: SimpleProxy v0.01\r\n";

		switch (file_type) 
		{
			case 0:
				break;
			case 1:
				s = s + "Content-Type: image/jpeg\r\n";
				break;
			case 2:
				s = s + "Content-Type: image/gif\r\n";
			case 3:
				s = s + "Content-Type: application/x-zip-compressed\r\n";
			default:
				s = s + "Content-Type: text/html\r\n";
				break;
		}

		s = s + "\r\n";
		return s;
	}
}