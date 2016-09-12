package com.vsp.kafka.clients;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class SimpleConsumer 
{
	private final ConsumerConnector consumer;
	private final String topic;
	
	public SimpleConsumer( String zookeeper, String groupId, String topic ) 
	{
		consumer = Consumer.createJavaConsumerConnector( createConsumerConfig ( zookeeper, groupId ));
		this.topic = topic;
	}

	public void consume() throws UnsupportedEncodingException
	{
		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		topicMap.put(topic, new Integer(1));
		
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreamsMap = consumer.createMessageStreams(topicMap);
		List<KafkaStream<byte[], byte[]>> streamList = consumerStreamsMap.get(topic);
		
		for( final KafkaStream<byte[], byte[]> stream : streamList )
		{
			ConsumerIterator<byte[],byte[]> it = stream.iterator();
			while( it.hasNext() )
			{
				String msg = new String(it.next().message(), "UTF-8");
				System.out.println(msg);
			}
		}
	}
	
	private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) 
	{
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeper);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "500" );
		props.put("zookeeper.sync.time.ms", "250" );
		props.put("auto.commit.interval.ms", "1000" );
		
		return new ConsumerConfig( props );
	}

	public static void main(String[] args) 
	{
		if( args.length < 3 )
		{
			System.out.println( "SimpleConsumer zookeeperHost:port, groupId, topic partitionCounts");
			System.exit(0); 
		}
		
		String zookeeper = args[0];
		String groupId = args[1];
		String topic = args[2];
		
		SimpleConsumer con = new SimpleConsumer( zookeeper, groupId, topic );
		try 
		{
			con.consume();
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}

	}

}
