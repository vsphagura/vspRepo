package com.vsp.kafka.clients.lib.example;

import java.util.Properties;

import com.vsp.kafka.clients.lib.KafkaProducer;

public class ProducerThread implements Runnable 
{

	long interMsgDelay = 0L;
	long nMessages = 0L;
	String message = "Test msg: ";
	String topic;
	
	private KafkaProducer producer = null;
	
	public ProducerThread( String topic, String msg, long msgDelay, long nMsgs ) 
	{
		this.message = msg;
		this.interMsgDelay = msgDelay;
		this.nMessages = nMsgs;
		this.topic = topic;
		
		Properties prop = new Properties();
		prop.put("metadata.broker.list", "localhost:9092, localhost:9093, localhost:9094");
		prop.put("serializer.class", "kafka.serializer.StringEncoder");
		prop.put("request.required.acks", "1");
		prop.put("partitioner.class", "com.vsp.kafka.clients.lib.example.ExamplePartitioner");
		
		producer = new KafkaProducer(prop);
	}

	@Override
	public void run() 
	{
		if( this.nMessages != 0L )
		{
			for( int i = 0; i < nMessages; i ++ )
			{
				this.publishMessages( String.valueOf(i) ); 
			}
		}
		else
		{
			while( true )
			{
				this.publishMessages(null);
			}
		}
		
		System.out.println("Published " + this.nMessages + " Messages.");
	}
	
	public void shutdown()
	{
		producer.close();
	}
	
	private void publishMessages( String key )
	{
		producer.publish(topic, key, message + key); 
		
		try 
		{
			Thread.sleep(interMsgDelay);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

}
