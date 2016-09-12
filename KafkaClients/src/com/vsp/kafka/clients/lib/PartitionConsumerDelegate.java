package com.vsp.kafka.clients.lib;

import java.util.concurrent.ExecutorService;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class PartitionConsumerDelegate implements Runnable 
{

	private final KafkaStream<byte[], byte[]> stream;
	private final ExecutorService executor;
	private int partitionNumber = 0;
	
	public PartitionConsumerDelegate( int tNumber, KafkaStream<byte[], byte[]> stream, ExecutorService exe ) 
	{
		this.stream = stream;
		this.executor = exe;
		this.partitionNumber = tNumber;
	}

	@Override
	public void run() 
	{
		ConsumerIterator<byte[], byte[]> consumerIt = stream.iterator();
		while( consumerIt.hasNext() )
		{
			//System.out.print("Received message ");
			byte[] msg = consumerIt.next().message();
			processMessage( msg );
		}
	}
	
	public void processMessage( Object message )
	{
		executor.submit(new MessageListener( this.partitionNumber, message ));
	}

}
