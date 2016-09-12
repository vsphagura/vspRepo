package com.vsp.kafka.clients.lib;

import java.util.concurrent.ExecutorService;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class PartitionConsumerDelegate implements Runnable 
{

	private final KafkaStream<byte[], byte[]> stream;
	private final ExecutorService executor;
	
	public PartitionConsumerDelegate( KafkaStream<byte[], byte[]> stream, ExecutorService exe ) 
	{
		this.stream = stream;
		this.executor = exe;
	}

	@Override
	public void run() 
	{
		ConsumerIterator<byte[], byte[]> consumerIt = stream.iterator();
		while( consumerIt.hasNext() )
		{
			executor.submit(new MessageListener( consumerIt.next().message() ));
		}

	}

}
