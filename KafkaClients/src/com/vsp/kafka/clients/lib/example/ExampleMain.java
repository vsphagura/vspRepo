package com.vsp.kafka.clients.lib.example;

import java.util.Properties;

import com.vsp.kafka.clients.lib.KafkaConsumer;

public class ExampleMain 
{

	private static ProducerThread producer;
	private static KafkaConsumer consumer;
	
	public ExampleMain( long nMsgs ) 
	{
		producer = new ProducerThread( "kafkatopic", "Msg: ", 1L, nMsgs );
		
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", "testgroup");
		props.put("zookeeper.session.timeout.ms", "500" );
		props.put("zookeeper.sync.time.ms", "250" );
		props.put("auto.commit.interval.ms", "1000" );
		
		consumer = new KafkaConsumer("kafkatopic", props, 10 );
	}

	public static void main(String[] args) 
	{
		if( args.length < 1 )
		{
			System.out.println( "ExampleMain <number of msgs - 0 for infinite>");
			System.exit(0); 
		}
		
		new ExampleMain( Long.valueOf(args[0]));
		new Thread( producer ).start();
		consumer.consume(3); 

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		producer.shutdown();
		consumer.shutdown();
		
		System.out.println( "Servers shoutdown");
	}

}
