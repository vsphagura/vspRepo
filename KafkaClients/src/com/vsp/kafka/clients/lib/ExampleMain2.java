package com.vsp.kafka.clients.lib;

import java.util.Properties;

import com.vsp.kafka.clients.lib.example.ProducerThread;

public class ExampleMain2 
{

	private static ProducerThread producer;
	private static KafkaConsumer[] consumer;
	private static int nPartitions;
	
	public ExampleMain2( int nParts, long nMsgs ) 
	{
		nPartitions = nParts;
		producer = new ProducerThread( "kafkatopic", "Msg: ", 1L, nMsgs );
		
		
		
		consumer = new KafkaConsumer[nPartitions];
		
		consumer[0] = new KafkaConsumer("kafkatopic", this.getProperties("testgroup1"), 10 ); 
		consumer[1] = new KafkaConsumer("kafkatopic", this.getProperties("testgroup2"), 10 ); 
		consumer[2] = new KafkaConsumer("kafkatopic", this.getProperties("testgroup3"), 10 ); 
	}

	public static void main(String[] args) 
	{
		if( args.length < 1 )
		{
			System.out.println( "ExampleMain <number of msgs - 0 for infinite>");
			System.exit(0); 
		}
		
		new ExampleMain2( 3, Long.valueOf(args[0]));
		new Thread( producer ).start();
		
		consumer[0].consume(3); 
		consumer[1].consume(3); 
		consumer[2].consume(3); 
	

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		producer.shutdown();
		consumer[0].shutdown(); 
		consumer[1].shutdown(); 
		consumer[2].shutdown(); 
		
		
		System.out.println( "Servers shoutdown");
	}

	private Properties getProperties( String groupId )
	{
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", groupId );
		props.put("zookeeper.session.timeout.ms", "500" );
		props.put("zookeeper.sync.time.ms", "250" );
		props.put("auto.commit.interval.ms", "1000" );
		
		return props;
	}
}
