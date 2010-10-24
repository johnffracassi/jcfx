package com.jeff.fx.remote;

import com.jeff.fx.remote.view.RemoteNode;

public interface AvailabilityListener extends BasicListener
{
	void newServer(RemoteNode remoteNode);
	void newClient(RemoteNode remoteNode);
}
