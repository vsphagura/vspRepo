package com.vsp.kafka.clients;

import java.util.Date;
import java.util.Properties;

import kafka.producer.KeyedMessage;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

public class SimpleProducer 
{
	static Producer<String, String> producer = null;
	
	public SimpleProducer() 
	{
		Properties prop = new Properties();
		prop.put("metadata.broker.list", "192.168.10.215:9092, 192.168.10.215:9093, 192.168.10.215:9094");
		prop.put("serializer.class", "kafka.serializer.StringEncoder");
		prop.put("request.required.acks", "1");
		
		ProducerConfig config = new ProducerConfig(prop);
		producer = new Producer<>(config);
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
		
		SimpleProducer sp = new SimpleProducer();
		sp.publish(topic, msgCount);

	}

	private void publish(String topic, int msgCount) 
	{
		for( int i=0; i < msgCount; i++)
		{
			String msg = "Test Msg, Time: " + new Date().toString();
			System.out.println("Msg: " + msg );
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, msg);
			producer.send(data);
			System.out.println("Msg# Sent: " + (i+1) );
		}
		
		producer.close();
	}

}
