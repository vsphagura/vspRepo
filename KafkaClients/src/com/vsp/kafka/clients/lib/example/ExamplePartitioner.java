package com.vsp.kafka.clients.lib.example;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class ExamplePartitioner implements Partitioner 
{

	public ExamplePartitioner( VerifiableProperties props ) 
	{
	}

	@Override
	public int partition(Object key, int nPartitions) 
	{
		int partition = 0;
		String partitionKey = (String)key;
		int di = Integer.parseInt( partitionKey );
		if( di > 0 )
		{
			partition = di % nPartitions;
		}
		
		return partition;
	}

}
