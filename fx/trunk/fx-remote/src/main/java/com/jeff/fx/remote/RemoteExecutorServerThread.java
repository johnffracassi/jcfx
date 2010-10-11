package com.jeff.fx.remote;

import java.net.*;
import java.io.*;

public class RemoteExecutorServerThread extends Thread {

	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	private int serverState = CONNECTED;
	private static final int CONNECTED = 0;
	private static final int INITIALISED = 1;
	private static final int DONE = 2;
	private static final int WAITING = 3;
	
	private int threads = 1;
	private int jobSize = 100;
	
	public RemoteExecutorServerThread(Socket socket) {

		super(socket.getRemoteSocketAddress().toString());

		this.socket = socket;

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void run() {

		try {

			send(RemoteExecutorProtocol.SMSG_HELLO);

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				
				receive(inputLine);

				if (serverState == DONE)
					break;
			}

			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receive(String str) {

		System.out.println(socket.getRemoteSocketAddress() + " <== " + str);
		
		if(str.startsWith(RemoteExecutorProtocol.CMSG_INIT)) {
			serverState = INITIALISED;
			send(RemoteExecutorProtocol.SMSG_THANKS);
		} else if(str.equals(RemoteExecutorProtocol.CMSG_DONE)) {
			serverState = DONE;
		} else if(str.equals(RemoteExecutorProtocol.CMSG_REQUEST)) {
			serverState = WAITING;
		}
	}

	private void send(String str) {
		System.out.println(socket.getRemoteSocketAddress() + " ==> " + str);
		out.println(str);
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getJobSize() {
		return jobSize;
	}

	public void setJobSize(int jobSize) {
		this.jobSize = jobSize;
	}
}
