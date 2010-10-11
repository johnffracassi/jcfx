package com.jeff.fx.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RemoteExecutorClient {
	
	private int threads = 4;
	private int jobSize = 1000;

	private Socket kkSocket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public static void main(String[] args) throws Exception {
		RemoteExecutorClient client = new RemoteExecutorClient();
		client.run();
	}

	public RemoteExecutorClient() {
	}

	public void run() throws Exception {
		
		try {
			kkSocket = new Socket(getServerHost(), getServerPort());
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: taranis.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: taranis.");
			System.exit(1);
		}

		String fromServer;
		while ((fromServer = in.readLine()) != null) {
			receive(fromServer);
		}

		out.close();
		in.close();
		kkSocket.close();
	}

	private void receive(String str) {
		
		System.out.println(kkSocket.getRemoteSocketAddress() + " ==> " + str);
		
		if (RemoteExecutorProtocol.SMSG_HELLO.equals(str)) {
			send(RemoteExecutorProtocol.CMSG_INIT + " threads=" + threads + ";jobSize=" + jobSize);
		} else if(RemoteExecutorProtocol.SMSG_THANKS.equals(str)) {
			send(RemoteExecutorProtocol.CMSG_REQUEST);
		} 
	}

	private void send(String message) {
		System.out.println(kkSocket.getRemoteSocketAddress() + " <== " + message);
		out.println(message);
	}

	public String getServerHost() {
		return "localhost";
	}

	public int getServerPort() {
		return 4444;
	}
}
