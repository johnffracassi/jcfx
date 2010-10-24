package com.jeff.fx.remote;

import javax.jms.Message;

public interface BasicListener {

	public void messageReceived(Message message);
	
}
