package com.vsp.kafka.clients.lib;

public interface MessageListenerInterface extends Runnable 
{
	public Object listen( Object message ) throws Exception;
}
