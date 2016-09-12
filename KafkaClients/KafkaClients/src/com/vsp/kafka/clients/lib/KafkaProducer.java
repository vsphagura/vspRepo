package com.vsp.kafka.clients.lib;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * This is a the kafka producer that can inherited and the publish method can 
 * be overridden.
 * This can publish to multiple partitions with a appropriate Partitioner class.
 * The constructor needs properties which will then be converted to ProducerConfig.
 * 
 * @author vphagura
 *
 */
public class KafkaProducer 
{

	private final Producer<String, String> producer;
	
	/**
	 * These properties are needed:
	 * metadata.broker.list <comma Separated list of host:port>
	 * serializer.class
	 * request.required.acks
	 * partitioner.class - this is optional, needed only when using partitioned topics
	 * 
	 * @param props
	 */
	public KafkaProducer( Properties props ) 
	{
		ProducerConfig config = new ProducerConfig(props);
		producer = new Producer<String, String>(config);
	}

	/**
	 * This publishes String messages to topics.
	 * This can publish to different partitions with proper 'partitionKey'
	 * If there is only one partition then this field can be left null.
	 * 
	 * @param topic
	 * @param partitionKey
	 * @param msg
	 */
	public void publish(String topic, String partitionKey, String msg) 
	{
		KeyedMessage<String, String> data = null;
		
		if( partitionKey == null || partitionKey.isEmpty() )
			data = new KeyedMessage<String, String>(topic, msg);
		else
			data = new KeyedMessage<String, String>(topic, partitionKey, msg);
		
		producer.send(data);
	}
	
	/**
	 * This closes the producer.
	 */
	public void close()
	{
		producer.close();
	}
}
