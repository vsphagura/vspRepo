package com.vsp.kafka.clients.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * The consumer has two thread pools: 
 * - one of reading from each partition
 * - for messages.
 * 
 * These properties have to be conveyed to the consumer:
 * zookeeper.connect
 * group.id
 * zookeeper.session.timeout.ms
 * zookeeper.sync.time.ms
 * auto.commit.interval.ms
 * 
 * @author vphagura
 *
 */
public class KafkaConsumer 
{

	private ExecutorService partitionThreadPool;
	private ExecutorService messageThreadPool;
	private final ConsumerConnector consumer;
	private final String topic;
	
	/**
	 * Creates the Kafka consumer and the thread pool for messages.
	 * These properties have to be conveyed to the constructor:
	 * zookeeper.connect
	 * group.id
	 * zookeeper.session.timeout.ms
	 * zookeeper.sync.time.ms
	 * auto.commit.interval.ms
	 * 
	 * @param zookeeper
	 * @param groupId
	 * @param topic
	 * @param props
	 * @param msgThdPoolSize
	 */
	public KafkaConsumer( String topic, Properties props, Integer msgThdPoolSize ) 
	{
		ConsumerConfig config = new ConsumerConfig( props );
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector( config );
		this.topic = topic;
		
		messageThreadPool = Executors.newFixedThreadPool( msgThdPoolSize );
	}

	/**
	 * This consumes the messages from each partition via a delegate, which in turn 
	 * consumes it by creating MessageListener threads to ultimately process these msgs.
	 * Note: There should be same number of consumers as partitions.
	 * 
	 * @param partitionCount 
	 */
	public void consume( int partitionCount )
	{
		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		topicMap.put(topic, partitionCount);
		
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreamsMap = consumer.createMessageStreams(topicMap);
		List<KafkaStream<byte[], byte[]>> streamList = consumerStreamsMap.get(topic);
		
		partitionThreadPool = Executors.newFixedThreadPool(partitionCount);
		int tNumber = 0;
		for( final KafkaStream<byte[], byte[]> stream : streamList )
		{
			partitionThreadPool.submit(new PartitionConsumerDelegate( tNumber++, stream, messageThreadPool ));
		}
	}
	
	
	/**
	 * Shuts down the whole consumer infrastructure.
	 */
	public void shutdown()
	{
		if( consumer != null )
			consumer.shutdown();
		if( partitionThreadPool != null )
			partitionThreadPool.shutdown();
		if( messageThreadPool != null )
			messageThreadPool.shutdown();
	}
	


}
