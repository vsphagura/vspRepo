package com.vsp.kafka.clients.lib;

import java.io.UnsupportedEncodingException;

public class MessageListener implements MessageListenerInterface 
{

	private final Object message;
	private final int pNumber;
	
	public MessageListener( int partitionNumber, Object msg ) 
	{
		this.message = msg;
		this.pNumber = partitionNumber;
	}

	@Override
	public void run() 
	{
		try 
		{
			this.listen(message);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Object listen( Object message ) throws UnsupportedEncodingException
	{
		Object ret = message;
		
		//System.out.println("Received message in listener");
		String msg = new String( (byte[])message, "UTF-8" );
		System.out.println("Partition: " + this.pNumber + " Message: " + msg ); 

		return ret;
	}

}
