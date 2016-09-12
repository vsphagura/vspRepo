package com.vsp.kafka.clients;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class MultiPartitionTopicConsumer 
{

	private ExecutorService executor;
	private final ConsumerConnector consumer;
	private final String topic;
	
	public MultiPartitionTopicConsumer( String zookeeper, String groupId, String topic ) 
	{
		ConsumerConfig config = createConsumerConfig( zookeeper, groupId );
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector( config );
		this.topic = topic;
	}

	public void shutdown()
	{
		if( consumer != null )
			consumer.shutdown();
		if( executor != null )
			executor.shutdown();
	}
	
	public void consume( int threadCount )
	{
		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		topicMap.put(topic, threadCount);
		
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreamsMap = consumer.createMessageStreams(topicMap);
		List<KafkaStream<byte[], byte[]>> streamList = consumerStreamsMap.get(topic);
		
		executor = Executors.newFixedThreadPool(threadCount);
		int count = 0; 
		for( final KafkaStream<byte[], byte[]> stream : streamList )
		{
			int tNumber = count;
			executor.submit(new PartitionConsumer( stream, tNumber ));
			count++;
		}
		
		
		/* Lamda expresion implementation - not very clear and hard to debug
		streamList.stream()
				  .forEach(stm -> {
					  				executor.submit(
					  					 (Runnable) () -> 
					  					  {
					  						  stm.forEach( message -> 
					  						  {
					  							  System.out.println("Messager : " + message );
					  						  });
					  			          });
				  				  });*/
		
		
	}
	
	public static void main(String[] args) 
	{
		if( args.length < 4 )
		{
			System.out.println( "MultiPartitionTopicConsumer zookeeperHost:port, groupId, topic partitionCounts");
			System.exit(0); 
		}
		
		String zookeeper = args[0];
		String groupId = args[1];
		String topic = args[2];
		int partitionCount = Integer.parseInt(args[3]); 
		
		MultiPartitionTopicConsumer mpc = new MultiPartitionTopicConsumer(zookeeper, groupId, topic);
		mpc.consume( partitionCount );
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		mpc.shutdown();
	}

	/***************** Private **********************************/
	public static ConsumerConfig createConsumerConfig( String zookeeper, String groupId )
	{
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeper);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "500" );
		props.put("zookeeper.sync.time.ms", "250" );
		props.put("auto.commit.interval.ms", "1000" );
		
		return new ConsumerConfig( props );
	}
	
	class PartitionConsumer implements Runnable
	{
		private KafkaStream<byte[], byte[]> stream = null;
		private int threadNumber = 0;
		
		public PartitionConsumer(KafkaStream<byte[], byte[]> stream, int thdNumber) 
		{
			super();
			this.stream = stream;
			threadNumber = thdNumber;
		}

		@Override
		public void run() 
		{
			ConsumerIterator<byte[], byte[]> consumerIt = stream.iterator();
			while( consumerIt.hasNext() )
			{
				String msg = null;
				try 
				{
					msg = new String(consumerIt.next().message(), "UTF-8");
				} 
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
				
				System.out.println("Partition # " + threadNumber + ", Message: " + msg ); 
			}
		}
		
	}
}
