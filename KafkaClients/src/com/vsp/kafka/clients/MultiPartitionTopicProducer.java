package com.vsp.kafka.clients;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class MultiPartitionTopicProducer 
{

	private static Producer<String, String> producer = null;
	
	public MultiPartitionTopicProducer()
	{
		Properties props = new Properties();
		
		props.put( "metadata.broker.list", "localhost:9092, localhost:9093, localhost:9094");
		props.put("serializer.class", "kafka.serializer.StringEncoder"); 
		props.put("request.required.acks", "1"); 
		props.put("partitioner.class", "com.vsp.kafka.clients.utils.SimplePartitioner");
		
		ProducerConfig config = new ProducerConfig( props );
		producer = new Producer<String, String>(config);
	}
	
	public static void main(String[] args) 
	{
		if( args.length < 2 )
		{
			System.out.println("Please provide TOPIC and Msg COUNT in args");
			System.exit(0);
		}
		String topic = args[0];
		String count = args[1];
		int msgCount = Integer.parseInt(count);
		
		System.out.println("Topic Name: " + topic );
		System.out.println("Msg Count: " + count );
		
		MultiPartitionTopicProducer prd = new MultiPartitionTopicProducer();
		prd.publish(topic, msgCount);

	}

	private void publish(String topic, int msgCount) 
	{
		for( int i=0; i < msgCount; i++)
		{
			String msg = "Test Msg " + i;
			System.out.println("Msg: " + msg );
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, msg, msg);
			producer.send(data);
			System.out.println("Msg Sent ");
		}
		
		producer.close();
	}

}

