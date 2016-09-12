package com.vsp.kafka.clients.lib;

public class MessageListener implements MessageListenerInterface 
{

	private final Object message;
	public MessageListener( Object msg ) 
	{
		this.message = msg;
	}

	@Override
	public void run() 
	{
		this.listen(message);
	}
	
	public Object listen( Object message )
	{
		Object ret = message;
		
		if( message instanceof String )
		{
			String msg = (String) message;
			System.out.println("Message: " + msg ); 
		}
		
		return ret;
	}

}
